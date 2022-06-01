export class ContractType {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
    public maxValidity?: number
  ) {}

  toJSON() {
    return {
      id: this.id,
      code: this.code,
      description: this.description,
      max_validity: this.maxValidity,
    };
  }

  static fromObject(object: any): ContractType {
    return new ContractType(
      object.id,
      object.code,
      object.description,
      object.max_validity
    );
  }
}
