import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpUtilService } from '../..';
import { environment as env } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CompanyService {
  public static readonly COMPANY_PATH: string = env.baseApiHOff + 'company';

  constructor(
    private httpClient: HttpClient,
    private httpUtilService: HttpUtilService
  ) {}

  public getCompany(cnpj: string): Observable<any> {
    let url = CompanyService.COMPANY_PATH + `/${cnpj}`;
    return this.httpClient.get(
      url,
      this.httpUtilService.user.auth ? this.httpUtilService.headersConfig() : {}
    );
  }
}
