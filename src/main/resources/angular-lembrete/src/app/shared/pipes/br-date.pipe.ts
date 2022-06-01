import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'brDate',
})
export class BrDatePipe implements PipeTransform {
  transform(dateIn: string): string {
    if (!dateIn) return '';

    const datetimeArr = dateIn.split(' ');
    const dateArr = datetimeArr[0].split('-');

    return datetimeArr[1]
      ? `${dateArr[2]}/${dateArr[1]}/${dateArr[0]} ${datetimeArr[1]}`
      : `${dateArr[2]}/${dateArr[1]}/${dateArr[0]}`;
  }
}
