import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { AppComponent } from 'src/app/app.component';
import { Credentials } from 'src/app/authentication';
import { Role, User } from '../models';

@Injectable({
  providedIn: 'root',
})
export class HttpUtilService {
  private _authenticated: boolean;
  private _user: User = new User();

  constructor(private httpClient: HttpClient, private router: Router) {
    this._authenticated = false;
  }

  public get user(): User {
    return this._user;
  }
  public set user(value: User) {
    this._user = value;
  }

  public get authenticated(): boolean {
    return this._authenticated;
  }
  public set authenticated(value: boolean) {
    this._authenticated = value;
  }

  public getUserRoles(): Array<Role> | undefined {
    return this._user.roles !== null ? this._user.roles : [];
  }

  isAdmin(): boolean {
    let result: boolean = false;
    if (this._user.roles !== undefined && this._user.roles !== null) {
      this._user.roles.forEach((auth) => {
        if (auth.authority === 'ROLE_ADMIN') result = true;
      });
    }
    return result;
  }

  exit() {
    this.httpClient
      .post('//localhost:8443/logout', { withCredentials: 'true' })
      .pipe(
        finalize(() => {
          this.authenticated = false;
          this.user = new User();
          this.router.navigate(['/']);
          if (localStorage.getItem(AppComponent.U_KEY) !== null) {
            localStorage.removeItem(AppComponent.U_KEY);
          }
        })
      )
      .subscribe();
  }

  /**
   * Sets the auth header and returns the request configuration object
   * with authorization data, if exists
   *
   * @param credentials login credentials
   * @returns configuration with pre-stabilished auth headers
   */
  headersConfig(credentials?: Credentials) {
    let headers: HttpHeaders = new HttpHeaders();

    // primeiro login
    if (credentials) {
      let auth = `Basic ${btoa(
        credentials.username + ':' + credentials.password
      )}`;
      headers = new HttpHeaders(credentials ? { Authorization: auth } : {});
    } else {
      // usuário já está logado
      headers = this.user.auth
        ? headers
            .append('Authorization', this.user.auth)
            .append('content-type', 'application/json')
        : headers;
    }

    return { headers: headers };
  }

  /**
   * Returns only the headers object with pre-configured authorization data,
   * if exists.
   *
   * @returns header object with authorization data
   */
  getHeaders() {
    let headers = this.user.auth ? this.headersConfig().headers : {};
    return headers;
  }

  verifyRefresh() {
    if (localStorage.getItem(AppComponent.U_KEY) !== null) {
      this._user = JSON.parse(localStorage.getItem(AppComponent.U_KEY)!);
    }
  }
}
