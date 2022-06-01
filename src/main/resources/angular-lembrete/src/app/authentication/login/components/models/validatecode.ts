export class ValidateCode {
  constructor(private _isValid: boolean) {}

  public get isValid(): boolean {
    return this._isValid;
  }
  public set isValid(value: boolean) {
    this._isValid = value;
  }
}
