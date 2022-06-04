import { Company } from './company.model';
import { ContractType } from './contract-type.model';
import { Interested } from './interested.model.';

export class Contract {
  constructor(
    public id?: number,
    public contractNumber?: string,
    public company?: Company,
    public initialDate?: string,
    public finalDate?: string,
    public contractType?: ContractType,
    public interested?: Array<Interested>
  ) {}

  toJSON() {
    return {
      id: this.id,
      contract_number: this.contractNumber,
      company: this.company,
      initial_date: this.initialDate,
      final_date: this.finalDate,
      contract_type: this.contractType,
      interested_list: this.interested,
    };
  }

  /**
   * Converte um ojeto contrato com as propriedades json em
   * um contrato com as propriedades padrão.
   * @param object objeto de resposta do backend.
   * @returns um contrato.
   */
  static fromObject(object: any): Contract {
    return new Contract(
      object.id,
      object.contract_number,
      object.company,
      object.initial_date,
      object.final_date,
      ContractType.fromObject(object.contract_type),
      object.interested_list
    );
  }
}
