export class CodeVerifyDTO {
  constructor(
    public cpf: string,
    public code: string,
    public password: string
  ) {}
}
