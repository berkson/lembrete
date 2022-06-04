import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpUtilService } from '..';
import { environment as env } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ContractTypeService {
  public static readonly CONTRCT_TYPE_PATH = env.baseApiHOff + 'contract-type';

  constructor(
    private httpClient: HttpClient,
    private httpUtils: HttpUtilService
  ) {}

  getAllContractTypes(): Observable<any> {
    return this.httpClient.get(
      ContractTypeService.CONTRCT_TYPE_PATH,
      this.httpUtils.user.auth ? this.httpUtils.headersConfig() : {}
    );
  }
}
