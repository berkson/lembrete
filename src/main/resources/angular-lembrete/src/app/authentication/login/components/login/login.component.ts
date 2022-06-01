import { Component, OnInit } from '@angular/core';
import {
  Form,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import {
  ApiError,
  CpfValidator,
  ErrorMessages,
  MessageService,
  User,
} from 'src/app/shared';
import { HttpUtilService } from 'src/app/shared';
import { LoginService } from '../../services';
import { Credentials } from '../models';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  readonly errorStateMatcher: ErrorStateMatcher = {
    isErrorState: (form: FormControl) => form && form.invalid,
  };
  private _pattern = { P: { pattern: new RegExp('([A-Za-z.])+') } };
  private _cpfDisabled: boolean;
  private _loginOption: boolean;

  constructor(
    private fb: FormBuilder,
    private errorService: MessageService,
    private router: Router,
    private loginService: LoginService,
    private httpUtils: HttpUtilService
  ) {
    this.form = new FormGroup({});
    this._cpfDisabled = true;
    this._loginOption = false;
  }

  ngOnInit(): void {
    this.generateForm();
  }

  generateForm() {
    this.form = this.fb.group({
      username: !this.cpfDisabled
        ? ['', [Validators.required, CpfValidator]]
        : ['', [Validators.required]],
      password: ['', [Validators.required, Validators.min(6)]],
    });
  }

  get cpfDisabled() {
    return this._cpfDisabled;
  }

  get pattern() {
    return this._pattern;
  }

  get cpf() {
    return this.form.get('cpf');
  }
  
  get loginOption() {
    return this._loginOption;
  }

  enableCpfLogin(): void {
    this._cpfDisabled = !this._cpfDisabled;
    this.generateForm();
  }

  changeLoginOptionState(){
    this._loginOption = !this._loginOption;
  }

  login() {
    if (this.form.invalid) {
      return;
    }
    const credentials: Credentials = this.form.value;
    this.loginService.loginWithCredentialsOrHeader(credentials).subscribe({
      next: (data) => {
        this.httpUtils.authenticated =
          data.cpf !== null || data.username !== null;
        this.httpUtils.user = data as User;
        this.httpUtils.user.auth = `Basic ${btoa(
          credentials.username + ':' + credentials.password
        )}`;
        this.router.navigate(['contract']);
      },
      error: (err) => {
        try {
          this.httpUtils.authenticated = false;
          let errors: ApiError[] = err.error.errors;
          this.errorService.showSnackErrors(errors);
        } catch (e) {
          this.errorService.snackErrorMessage(ErrorMessages.tryAgain);
        }
      },
    });
  }
}
