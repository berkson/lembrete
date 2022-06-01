import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { environment as env } from 'src/environments/environment';
import { HttpUtilService } from '..';
import { Contract } from '../../models';
import { Additive } from '../../models/additive.model';

@Injectable({
  providedIn: 'root',
})
export class ContractService {
  public static readonly CONTRACTS_PATH: string = env.baseApiHOff + 'contract';
  public static readonly CONTRACTSLIST_PATH: string = `${ContractService.CONTRACTS_PATH}/all`;
  public static readonly NEWCONTRACT_PATH: string = `${ContractService.CONTRACTS_PATH}/new`;
  public static readonly CHECKCONTRACT_PATH: string = `${ContractService.CONTRACTS_PATH}/check`;
  public static readonly ADDITIVECONTRACT_PATH: string = `${ContractService.CONTRACTS_PATH}/add`;

  constructor(
    private httpClient: HttpClient,
    private httpUtils: HttpUtilService
  ) {}

  listAllContracts(
    page: number,
    direction: string,
    order: string
  ): Observable<any> {
    let url =
      ContractService.CONTRACTSLIST_PATH +
      this.mountPageParams(page, direction, order);
    return this.httpClient.get(
      url,
      this.httpUtils.user.auth ? this.httpUtils.headersConfig() : {}
    );
  }

  checkIfContractExists(number: string): Observable<any> {
    return this.httpClient.post(ContractService.CHECKCONTRACT_PATH, number, {
      headers: this.httpUtils.getHeaders(),
      observe: 'response',
    });
  }

  registerContract(contract: Contract): Observable<any> {
    return this.httpClient.post(
      ContractService.NEWCONTRACT_PATH,
      JSON.stringify(contract),
      { headers: this.httpUtils.getHeaders(), observe: 'response' }
    );
  }

  canAdditivate(contract: Contract): boolean {
    return (
      moment(contract.finalDate).diff(moment(contract.initialDate), 'month') ===
      contract.contractType?.maxValidity
    );
  }

  additivate(additive: Additive): Observable<any> {
    return this.httpClient.post(
      ContractService.ADDITIVECONTRACT_PATH,
      JSON.stringify(additive),
      { headers: this.httpUtils.getHeaders(), observe: 'response' }
    );
  }

  private mountPageParams(
    page: number,
    direction: string,
    order: string
  ): string {
    return `?pag=${page}&ord=${order}&dir=${direction}`;
  }
}
