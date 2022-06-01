import { AbstractControl } from '@angular/forms';
import * as moment from 'moment';

export class PastOrPresentValidator {
  static validate(control: AbstractControl): { [key: string]: boolean } | null {
    if (this.isValidDate(control.value)) {
      return null;
    }
    return { date: true };
  }

  static isValidDate(date: any): boolean {
    return moment() >= date;
  }
}
