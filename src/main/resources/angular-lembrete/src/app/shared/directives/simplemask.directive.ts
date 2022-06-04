import { Directive, ElementRef, HostListener, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
  selector: '[simple-mask]',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: MaskDirective,
      multi: true,
    },
  ],
})
export class MaskDirective implements ControlValueAccessor {
  onTouched: any;
  onChange: any;
  @Input('simple-mask') mask: string;

  constructor(private el: ElementRef) {
    this.mask = '';
  }

  writeValue(obj: any): void {
    if (obj) this.el.nativeElement.value = this.applyMask(obj);
  }
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  @HostListener('keyup', ['$event'])
  onKeyup($event: any) {
    let value: string = $event.target.value.replace(/\D/g, '');

    if ($event.keycode === 8) {
      this.onChange(value);
      return;
    }

    let pad = this.mask.replace(/\D/g, '').replace(/9/g, '_');
    if (value.length <= pad.length) this.onChange(value);
    $event.target.value = this.applyMask(value);
  }

  // se campo incompleto e perder o foco, é apagado
  @HostListener('blur', ['$event'])
  onBlur($event: any) {
    if ($event.target.value.length === this.mask.length) return;
    this.onChange('');
    $event.target.value = '';
  }

  // digita-se a numeração e é comparada com a máscara.
  applyMask(value: string) {
    value = value.replace(/\D/g, ''); // número digitado
    let pad = this.mask.replace(/\D/g, '').replace(/9/g, '_'); // tamanho da string
    let maskValue = value + pad.substring(0, pad.length - value.length);
    let maskValuePos = 0;

    value = '';

    // lógica de substituição
    for (let i = 0; i < this.mask.length; i++) {
      if (isNaN(parseInt(this.mask.charAt(i)))) {
        value += this.mask.charAt(i); // insere caractere não númerico conforme a máscara
      } else {
        value += maskValue[maskValuePos++]; // adiciona o próximo número ao valor
      }
    }

    if (value.indexOf('_') > -1) value = value.substring(0, value.indexOf('_'));

    return value;
  }
}
