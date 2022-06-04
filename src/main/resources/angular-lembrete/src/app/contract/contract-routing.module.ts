import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuardService } from '../shared/services/guards/user-guard.service';
import { ContractComponent, RegisterComponent } from './components';
import { ListingComponent } from './components';

export const ContractRoutes: Routes = [
  {
    path: 'contract',
    component: ContractComponent,
    canActivate: [UserGuardService],
    children: [
      { path: '', component: ListingComponent },
      {
        path: 'register',
        component: RegisterComponent,
      },
    ],
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(ContractRoutes)],
  exports: [RouterModule],
})
export class ContractRoutingModule {}
