import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpUtilService } from '../..';
import { environment as env } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InterestedService {
  public static readonly INTERESTED_PATH: string =
    env.baseApiHOff + 'interested';

  constructor(
    private httpClient: HttpClient,
    private httpUtilService: HttpUtilService
  ) {}

  public getInterested(cpf: string): Observable<any> {
    let url = InterestedService.INTERESTED_PATH + `/${cpf}`;
    return this.httpClient.get(
      url,
      this.httpUtilService.user.auth ? this.httpUtilService.headersConfig() : {}
    );
  }
}
