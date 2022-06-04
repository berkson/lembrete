import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent, LoginTemplateComponent } from './components';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { FlexLayoutModule } from '@angular/flex-layout';
import { LoginService } from './services';
import { HttpUtilService } from 'src/app/shared/services';
import { SharedModule } from 'src/app/shared';
import {
  ErrorStateMatcher,
  ShowOnDirtyErrorStateMatcher,
} from '@angular/material/core';
import { ForgotpassComponent } from './components/forgotpass/forgotpass.component';
import { InformcodeComponent } from './components/informcode/informcode.component';
import { NgxMaskModule } from 'ngx-mask';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';

@NgModule({
  declarations: [LoginComponent, LoginTemplateComponent, ForgotpassComponent, InformcodeComponent],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions(),
    MatInputModule,
    MatButtonModule,
    MatListModule,
    MatTooltipModule,
    MatToolbarModule,
    MatIconModule,
    MatSnackBarModule,
    FlexLayoutModule,
    SharedModule,
    NgxMaskModule,
    MatCheckboxModule,
    MatRadioModule
  ],
  providers: [
    LoginService,
    HttpUtilService,
    { provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher },
  ],
})
export class LoginModule {}
