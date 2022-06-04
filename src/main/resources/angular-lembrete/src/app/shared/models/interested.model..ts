import { Phone } from './phone.model';

export class Interested {
  constructor(
    public id?: number,
    public cpf?: string,
    public name?: string,
    public email?: string,
    public phones?: Array<Phone>
  ) {}

}
