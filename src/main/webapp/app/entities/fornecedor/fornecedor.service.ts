import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { map } from 'rxjs/operators';
import { fornecedorPopupRoute } from './fornecedor.route';

type EntityResponseType = HttpResponse<IFornecedor>;
type EntityArrayResponseType = HttpResponse<IFornecedor[]>;

@Injectable({ providedIn: 'root' })
export class FornecedorService {
  public resourceUrl = SERVER_API_URL + 'api/fornecedors';
  fornecedor: IFornecedor;

  constructor(protected http: HttpClient) {}

  create(fornecedor: IFornecedor): Observable<EntityResponseType> {
    return this.http.post<IFornecedor>(this.resourceUrl, fornecedor, { observe: 'response' });
  }

  update(fornecedor: IFornecedor): Observable<EntityResponseType> {
    return this.http.put<IFornecedor>(this.resourceUrl, fornecedor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFornecedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFornecedor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  findWithCpf(cpf: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.fornecedor && this.fornecedor.id ? String(this.fornecedor.id) : '0';
    parametros = parametros.set('cpf', cpf);
    parametros = parametros.append('id', id);
    const withcpf = 'withcpf';
    const url = `${this.resourceUrl}/${withcpf}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  findWithCnpj(cnpj: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.fornecedor && this.fornecedor.id ? String(this.fornecedor.id) : '0';
    parametros = parametros.set('cnpj', cnpj);
    parametros = parametros.append('id', id);
    const withcnpj = 'withcnpj';
    const url = `${this.resourceUrl}/${withcnpj}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  setFornecedor(fornecedor: IFornecedor) {
    this.fornecedor = fornecedor;
  }
}
