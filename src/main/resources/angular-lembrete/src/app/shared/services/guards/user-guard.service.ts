import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { Auths } from '../../enums';
import { ErrorMessages } from '../../messages';
import { HttpUtilService } from '../http-util.service';
import { MessageService } from '../message.service';

@Injectable({
  providedIn: 'root',
})
export class UserGuardService implements CanActivate {
  constructor(
    private httpUtil: HttpUtilService,
    private messageService: MessageService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    this.httpUtil.verifyRefresh();
    let roles = this.httpUtil.getUserRoles();
    if (roles !== undefined) {
      for (let role of roles) {
        if (
          role.authority === Auths.SIGECON_PUBLICO ||
          role.authority === Auths.ADMIN
        ) {
          return true;
        }
      }
      this.messageService.snackErrorMessage(
        ErrorMessages.notUser,
        ErrorMessages.resctrict
      );
      this.httpUtil.exit();
      return false;
    } else {
      this.messageService.snackErrorMessage(ErrorMessages.sessionExpired);
      this.httpUtil.exit();
      return false;
    }
  }
}
