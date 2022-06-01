import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {
  Contract,
  ContractService,
  Messages,
  MessageService,
} from 'src/app/shared';
import * as moment from 'moment';
import { Additive } from 'src/app/shared/models/additive.model';
import { map } from 'rxjs';

@Component({
  selector: 'additive-dialog',
  templateUrl: './additive-dialog.component.html',
  styleUrls: ['./additive-dialog.component.scss'],
})
export class AditiveDialogComponent implements OnInit {
  additiveForm: FormGroup = new FormGroup({});

  constructor(
    public dialogRef: MatDialogRef<AditiveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public contract: Contract,
    private fb: FormBuilder,
    private contractService: ContractService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  calcMaxMonths() {
    let months: number = moment(this.contract.finalDate).diff(
      moment.now(),
      'months'
    );
    return months < 0 ? 0 : months;
  }

  createForm() {
    this.additiveForm = this.fb.group({
      months: ['', [Validators.max(this.calcMaxMonths())]],
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  callAdditivate() {
    let additive: Additive = new Additive(
      this.contract.id!,
      this.additiveForm.get('months')?.value
    );
    this.contractService
      .additivate(additive)
      .pipe(
        map((response) => {
          if (response.status === 200) {
            this.messageService.snackSuccessMessage(Messages.additivateSuccess);
            this.onNoClick();
          }
        })
      )
      .subscribe({
        error: (err) => {
          this.messageService.handleValidationErrors(err.error.errors);
        },
      });
  }
}
