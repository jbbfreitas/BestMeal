export const enum GrupoMenu {
  ENTRADA = 'ENTRADA',
  PRATOFRIO = 'PRATOFRIO',
  PRATOQUENTE = 'PRATOQUENTE',
  SOBREMESA = 'SOBREMESA',
  VINHO = 'VINHO',
  CERVEJA = 'CERVEJA',
  DESTILADO = 'DESTILADO',
  REFRIGERANTE = 'REFRIGERANTE',
  SUCO = 'SUCO',
  COCKTAILSEMALCOOL = 'COCKTAILSEMALCOOL',
  COCKTAILCOMALCOOL = 'COCKTAILCOMALCOOL'
}

export interface IMenu {
  id?: number;
  nome?: string;
  grupo?: GrupoMenu;
  valorNormal?: number;
  tempoPreparo?: number;
  isDisponivel?: boolean;
  valorReduzido?: number;
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public nome?: string,
    public grupo?: GrupoMenu,
    public valorNormal?: number,
    public tempoPreparo?: number,
    public isDisponivel?: boolean,
    public valorReduzido?: number
  ) {
    this.isDisponivel = this.isDisponivel || false;
  }
}
