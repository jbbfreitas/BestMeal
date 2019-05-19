export const enum UF {
  MT = 'MT',
  GO = 'GO',
  MS = 'MS',
  AM = 'AM',
  PA = 'PA',
  PR = 'PR',
  RN = 'RN',
  RS = 'RS',
  MG = 'MG',
  DF = 'DF',
  PB = 'PB',
  AL = 'AL',
  SE = 'SE',
  PI = 'PI',
  MA = 'MA',
  RO = 'RO',
  RR = 'RR',
  RJ = 'RJ',
  AP = 'AP',
  SP = 'SP',
  AC = 'AC',
  TO = 'TO',
  ES = 'ES',
  SC = 'SC',
  BA = 'BA',
  PE = 'PE'
}

export interface IMunicipio {
  id?: number;
  nomeMunicipio?: string;
  uf?: UF;
}

export class Municipio implements IMunicipio {
  constructor(public id?: number, public nomeMunicipio?: string, public uf?: UF) {}
}
