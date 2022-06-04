import { Component, HostListener } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { HttpUtilService } from './shared/services';
import { filter } from 'rxjs';
import { ErrorMessages, MessageService, User } from './shared';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss', './app-header.component.scss'],
})
export class AppComponent {
  public static readonly U_KEY: string = 'user';
  constructor(
    private httpUtils: HttpUtilService,
    private router: Router,
    private messageService: MessageService
  ) {
    // Verifica se a aplicação foi reinicializada e procede a verificação da sessão, se está ativa
    // https://stackoverflow.com/questions/56325272/detect-browser-refresh-in-angular-project
    if (localStorage.getItem(AppComponent.U_KEY) !== null) {
      //console.log('inicializando aplicação');
      let user: User = JSON.parse(localStorage.getItem(AppComponent.U_KEY)!);
      this.router.events
        .pipe(filter((rs): rs is NavigationEnd => rs instanceof NavigationEnd))
        .subscribe(
          (event: { id: number; url: any; urlAfterRedirects: any }) => {
            if (event.id === 1 && event.url === event.urlAfterRedirects) {
              try {
                if (user.auth) {
                  this.httpUtils.user = user;
                  this.httpUtils.authenticated = user.cpf !== null || user.username !== null;
                  this.delete();
                }
              } catch (e) {
                this.messageService.snackErrorMessage(ErrorMessages.tryAgain);
                this.httpUtils.exit();
              }
            }
          }
        );
    }
  }

  // Salva o usuário no localstorage antes do refresh
  @HostListener('window:beforeunload', ['$event']) unloadHandler(event: Event) {
    //console.log('Processing beforeunload...', this.httpUtils.user);
    this.processUserData();
  }

  // Se a página não está visível pode estar abrindo uma nova aba.
  @HostListener('contextmenu', ['$event']) handleVisibilityChange(
    event: Event
  ) {
    //console.log('Processing context menu...', this.httpUtils.user);
    this.processHiddenDocument();
  }
  processHiddenDocument() {
    //console.log('habilitando usuário');
    localStorage.setItem(
      AppComponent.U_KEY,
      JSON.stringify(this.httpUtils.user)
    );
    this.startCountdown(20);
  }

  delete() {
    localStorage.removeItem(AppComponent.U_KEY);
    //console.log('deletado');
  }

  processUserData() {
    if (this.httpUtils.user.auth !== undefined)
      localStorage.setItem(
        AppComponent.U_KEY,
        JSON.stringify(this.httpUtils.user)
      );
  }

  authenticated(): boolean {
    return this.httpUtils.authenticated;
  }

  exit() {
    this.httpUtils.exit();
  }

  startCountdown(seconds: number) {
    let counter = seconds;

    const interval = setInterval(() => {
      counter--;
      //console.log(counter);
      if (counter < 0) {
        clearInterval(interval);
        this.delete();
      }
    }, 1000);
  }
}
