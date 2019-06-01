import { IMunicipio } from 'app/shared/model/municipio.model';

export const enum TipoPessoa {
  FISICA = 'FISICA',
  JURIDICA = 'JURIDICA'
}

export const enum TipoLogradouro {
  RUA = 'RUA',
  AVENIDA = 'AVENIDA',
  TRAVESSA = 'TRAVESSA',
  QUADRA = 'QUADRA',
  BECO = 'BECO',
  ESTRADA = 'ESTRADA',
  CHACARA = 'CHACARA',
  RODOVIA = 'RODOVIA',
  VIADUTO = 'VIADUTO',
  SITIO = 'SITIO',
  FEIRA = 'FEIRA',
  SETOR = 'SETOR',
  MORRO = 'MORRO',
  LARGO = 'LARGO',
  FAZENDA = 'FAZENDA'
}

export interface IFornecedor {
  id?: number;
  tipo?: TipoPessoa;
  cpf?: string;
  cnpj?: string;
  primeiroNome?: string;
  nomeMeio?: string;
  sobreNome?: string;
  saudacao?: string;
  titulo?: string;
  cep?: string;
  tipoLogradouro?: TipoLogradouro;
  nomeLogradouro?: string;
  complemento?: string;
  municipio?: IMunicipio;
}

export class Fornecedor implements IFornecedor {
  constructor(
    public id?: number,
    public tipo?: TipoPessoa,
    public cpf?: string,
    public cnpj?: string,
    public primeiroNome?: string,
    public nomeMeio?: string,
    public sobreNome?: string,
    public saudacao?: string,
    public titulo?: string,
    public cep?: string,
    public tipoLogradouro?: TipoLogradouro,
    public nomeLogradouro?: string,
    public complemento?: string,
    public municipio?: IMunicipio
  ) {}
}
