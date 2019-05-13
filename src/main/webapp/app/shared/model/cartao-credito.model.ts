export const enum Bandeira {
  AMERICAN = 'AMERICAN',
  DINERS = 'DINERS',
  ELO = 'ELO',
  MASTER = 'MASTER',
  VISA = 'VISA'
}

export interface ICartaoCredito {
  id?: number;
  nomeCartao?: string;
  bandeira?: Bandeira;
  numero?: string;
  cvv?: string;
  validade?: string;
}

export class CartaoCredito implements ICartaoCredito {
  constructor(
    public id?: number,
    public nomeCartao?: string,
    public bandeira?: Bandeira,
    public numero?: string,
    public cvv?: string,
    public validade?: string
  ) {}
}
