import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';

type EntityResponseType = HttpResponse<ICartaoRecompensa>;
type EntityArrayResponseType = HttpResponse<ICartaoRecompensa[]>;

@Injectable({ providedIn: 'root' })
export class CartaoRecompensaService {
  public resourceUrl = SERVER_API_URL + 'api/cartao-recompensas';

  constructor(protected http: HttpClient) {}

  create(cartaoRecompensa: ICartaoRecompensa): Observable<EntityResponseType> {
    return this.http.post<ICartaoRecompensa>(this.resourceUrl, cartaoRecompensa, { observe: 'response' });
  }

  update(cartaoRecompensa: ICartaoRecompensa): Observable<EntityResponseType> {
    return this.http.put<ICartaoRecompensa>(this.resourceUrl, cartaoRecompensa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICartaoRecompensa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICartaoRecompensa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
