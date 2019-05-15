import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProduto } from 'app/shared/model/produto.model';

type EntityResponseType = HttpResponse<IProduto>;
type EntityArrayResponseType = HttpResponse<IProduto[]>;

@Injectable({ providedIn: 'root' })
export class ProdutoService {
  public resourceUrl = SERVER_API_URL + 'api/produtos';

  constructor(protected http: HttpClient) {}

  create(produto: IProduto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produto);
    return this.http
      .post<IProduto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(produto: IProduto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(produto);
    return this.http
      .put<IProduto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProduto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(produto: IProduto): IProduto {
    const copy: IProduto = Object.assign({}, produto, {
      dataUltimaCompra:
        produto.dataUltimaCompra != null && produto.dataUltimaCompra.isValid() ? produto.dataUltimaCompra.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataUltimaCompra = res.body.dataUltimaCompra != null ? moment(res.body.dataUltimaCompra) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((produto: IProduto) => {
        produto.dataUltimaCompra = produto.dataUltimaCompra != null ? moment(produto.dataUltimaCompra) : null;
      });
    }
    return res;
  }
}
