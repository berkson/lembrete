import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import {
  ApiError,
  ErrorMessages,
  MessageService,
  Messages,
} from 'src/app/shared';
import { environment as env } from 'src/environments/environment';

@Component({
  selector: 'app-forgotpass',
  templateUrl: './forgotpass.component.html',
  styleUrls: ['./forgotpass.component.scss'],
})
export class ForgotpassComponent implements OnInit {
  public readonly SEND_CODE_PATH: string = 'user/passrecover';
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private httpClient: HttpClient,
    private messageService: MessageService,
    private router: Router
  ) {
    this.form = new FormGroup({});
  }

  ngOnInit(): void {
    this.generateForm();
  }

  generateForm() {
    this.form = this.fb.group({
      email: ['', [Validators.email, Validators.required]],
    });
  }

  forgot() {
    if (this.form.invalid) return;
    const form = this.form.value;
    this.httpClient
      .post(env.baseApiHOff + this.SEND_CODE_PATH, form.email, {
        withCredentials: true,
        observe: 'response',
      })
      .pipe(
        map((response) => {
          if (response.status == 200) {
            this.messageService.snackSuccessMessage(Messages.emailSuccess);
            this.router.navigate(['/login/informcode']);
          }
        })
      )
      .subscribe({
        error: (err) => {
          try {
            let errors: ApiError[] = err.error.errors;
            this.messageService.showSnackErrors(errors);
          } catch (e) {
            this.messageService.snackErrorMessage(ErrorMessages.tryAgain);
          }
        },
      });
  }
}
