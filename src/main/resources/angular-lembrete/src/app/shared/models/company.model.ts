export class Company {
  constructor(public id?: number, public cnpj?: string, public name?: string) {}

  toJSON() {
    return { id: this.id, cnpj: this.cnpj, name: this.name };
  }
}
