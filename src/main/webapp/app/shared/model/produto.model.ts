import { Moment } from 'moment';

export interface IProduto {
  id?: number;
  codigo?: string;
  nome?: string;
  unidade?: string;
  estoqueMinimo?: number;
  estoqueAtual?: number;
  dataUltimaCompra?: Moment;
  valorUltimaCompra?: number;
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public codigo?: string,
    public nome?: string,
    public unidade?: string,
    public estoqueMinimo?: number,
    public estoqueAtual?: number,
    public dataUltimaCompra?: Moment,
    public valorUltimaCompra?: number
  ) {}
}
