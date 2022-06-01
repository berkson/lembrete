import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent, LoginTemplateComponent } from './components';
import { ForgotpassComponent } from './components/forgotpass';
import { InformcodeComponent } from './components/informcode';

export const loginRoutes: Routes = [
  {
    path: 'login',
    component: LoginTemplateComponent,
    children: [
      { path: '', component: LoginComponent },
      { path: 'forgotpass', component: ForgotpassComponent },
      { path: 'informcode', component: InformcodeComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loginRoutes)],
  exports: [RouterModule],
})
export class LoginRoutingModule {}
