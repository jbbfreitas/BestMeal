export const enum SituacaoCartao {
  ATIVO = 'ATIVO',
  INATIVO = 'INATIVO',
  CANCELADO = 'CANCELADO',
  PENDENTE = 'PENDENTE'
}

export interface ICartaoRecompensa {
  id?: number;
  nomeCartao?: string;
  numero?: string;
  validade?: string;
  pontuacao?: number;
  situacao?: SituacaoCartao;
}

export class CartaoRecompensa implements ICartaoRecompensa {
  constructor(
    public id?: number,
    public nomeCartao?: string,
    public numero?: string,
    public validade?: string,
    public pontuacao?: number,
    public situacao?: SituacaoCartao
  ) {}
}
