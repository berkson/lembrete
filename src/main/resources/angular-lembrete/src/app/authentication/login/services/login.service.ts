import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Credentials } from '../components';
import { environment as env } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HttpUtilService } from 'src/app/shared/services';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  public static readonly AUTH_PATH: string = env.baseApiUrl + 'user';

  constructor(
    private httpClient: HttpClient,
    private httpUtils: HttpUtilService
  ) {}

  loginWithCredentialsOrHeader(credentials: Credentials): Observable<any> {
    return this.httpClient.get(
      LoginService.AUTH_PATH,
      this.httpUtils.headersConfig(credentials)
    );
  }
}
