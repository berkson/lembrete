import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import {
  ApiError,
  CpfValidator,
  ErrorMessages,
  MessageService,
  Messages,
} from 'src/app/shared';
import { environment as env } from 'src/environments/environment';
import { ValidateCode } from '../models';
import { CodeVerifyDTO } from '../models/codeverifydto';

@Component({
  selector: 'app-informcode',
  templateUrl: './informcode.component.html',
  styleUrls: ['./informcode.component.scss'],
})
export class InformcodeComponent implements OnInit {
  form: FormGroup;
  formPass: FormGroup;
  private _hidePassForm: boolean;
  private _hideForm: boolean;
  public readonly VALIDATE_CODE_PATH = 'user/validatecode/';
  public readonly CHANGE_PASS_PATH = 'user/changepass';
  codeFormValues: any;

  constructor(
    private fb: FormBuilder,
    private httpClient: HttpClient,
    private messageService: MessageService,
    private router: Router
  ) {
    this.form = new FormGroup({});
    this.formPass = new FormGroup({});
    this._hideForm = false;
    this._hidePassForm = true;
  }

  ngOnInit(): void {
    this.generateForm();
    this.generateFormPass();
  }

  public get hidePassForm(): boolean {
    return this._hidePassForm;
  }
  public set hidePassForm(value: boolean) {
    this._hidePassForm = value;
  }

  public get hideForm(): boolean {
    return this._hideForm;
  }
  public set hideForm(value: boolean) {
    this._hideForm = value;
  }

  generateForm() {
    this.form = this.fb.group({
      cpf: ['', [Validators.required, CpfValidator]],
      code: ['', [Validators.required]],
    });
  }

  generateFormPass() {
    this.formPass = this.fb.group({
      password: ['', [Validators.required]],
      confpass: ['', [Validators.required]],
    });
  }

  inform() {
    if (this.form.invalid) return;
    this.codeFormValues = this.form.value;
    this.httpClient
      .post(
        env.baseApiHOff + this.VALIDATE_CODE_PATH + this.codeFormValues.cpf,
        this.codeFormValues.code,
        {
          withCredentials: true,
        }
      )
      .subscribe({
        next: (data) => {
          let code: ValidateCode = data as ValidateCode;
          if (code.isValid) {
            this._hideForm = !this._hideForm;
            this._hidePassForm = !this._hidePassForm;
            this.messageService.snackSuccessMessage(
              Messages.codeSuccess + ' ' + Messages.enterNewPass
            );
          } else {
            this.messageService.snackErrorMessage(ErrorMessages.codeError);
          }
        },
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

  newpass() {
    if (this.formPass.invalid) return;
    const formPass = this.formPass.value;
    if (formPass.password === formPass.confpass) {
      const codeVerifyDTO: CodeVerifyDTO = new CodeVerifyDTO(
        this.codeFormValues.cpf,
        this.codeFormValues.code,
        formPass.password
      );
      this.httpClient
        .post(env.baseApiHOff + this.CHANGE_PASS_PATH, codeVerifyDTO, {
          withCredentials: true,
          observe: 'response',
        })
        .pipe(
          map((response) => {
            if (response.status == 200) {
              this.messageService.snackSuccessMessage(
                Messages.changePassSuccess
              );
              this.router.navigate(['/login']);
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
    } else {
      this.messageService.snackErrorMessage(ErrorMessages.passNotEqual);
    }
  }
}
