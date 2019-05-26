import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { map } from 'rxjs/operators';
import { pessoaPopupRoute } from './pessoa.route';

type EntityResponseType = HttpResponse<IPessoa>;
type EntityArrayResponseType = HttpResponse<IPessoa[]>;

@Injectable({ providedIn: 'root' })
export class PessoaService {
  public resourceUrl = SERVER_API_URL + 'api/pessoas';
  pessoa: IPessoa;

  constructor(protected http: HttpClient) {}

  create(pessoa: IPessoa): Observable<EntityResponseType> {
    return this.http.post<IPessoa>(this.resourceUrl, pessoa, { observe: 'response' });
  }

  update(pessoa: IPessoa): Observable<EntityResponseType> {
    return this.http.put<IPessoa>(this.resourceUrl, pessoa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  findWithCpf(cpf: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.pessoa && this.pessoa.id ? String(this.pessoa.id) : '0';
    parametros = parametros.set('cpf', cpf);
    parametros = parametros.append('id', id);
    const withcpf = 'withcpf';
    const url = `${this.resourceUrl}/${withcpf}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  findWithCnpj(cnpj: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.pessoa && this.pessoa.id ? String(this.pessoa.id) : '0';
    parametros = parametros.set('cnpj', cnpj);
    parametros = parametros.append('id', id);
    const withcnpj = 'withcnpj';
    const url = `${this.resourceUrl}/${withcnpj}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  setPessoa(pessoa: IPessoa) {
    this.pessoa = pessoa;
  }
}
