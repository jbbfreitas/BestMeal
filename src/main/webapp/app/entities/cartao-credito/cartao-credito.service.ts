import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';

type EntityResponseType = HttpResponse<ICartaoCredito>;
type EntityArrayResponseType = HttpResponse<ICartaoCredito[]>;

@Injectable({ providedIn: 'root' })
export class CartaoCreditoService {
  public resourceUrl = SERVER_API_URL + 'api/cartao-creditos';

  constructor(protected http: HttpClient) {}

  create(cartaoCredito: ICartaoCredito): Observable<EntityResponseType> {
    return this.http.post<ICartaoCredito>(this.resourceUrl, cartaoCredito, { observe: 'response' });
  }

  update(cartaoCredito: ICartaoCredito): Observable<EntityResponseType> {
    return this.http.put<ICartaoCredito>(this.resourceUrl, cartaoCredito, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICartaoCredito>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICartaoCredito[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
