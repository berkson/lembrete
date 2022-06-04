import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaskDirective } from './directives/simplemask.directive';
import { HttpClientModule } from '@angular/common/http';
import { BrDatePipe } from './pipes/br-date.pipe';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

@NgModule({
  declarations: [MaskDirective, BrDatePipe],
  imports: [
    CommonModule,
    HttpClientModule,
    MatSnackBarModule,
    MatIconModule,
    MatListModule,
  ],
  exports: [MaskDirective, BrDatePipe],
})
export class SharedModule {}
