import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestaurante } from 'app/shared/model/restaurante.model';

type EntityResponseType = HttpResponse<IRestaurante>;
type EntityArrayResponseType = HttpResponse<IRestaurante[]>;

@Injectable({ providedIn: 'root' })
export class RestauranteService {
  public resourceUrl = SERVER_API_URL + 'api/restaurantes';

  constructor(protected http: HttpClient) {}

  create(restaurante: IRestaurante): Observable<EntityResponseType> {
    return this.http.post<IRestaurante>(this.resourceUrl, restaurante, { observe: 'response' });
  }

  update(restaurante: IRestaurante): Observable<EntityResponseType> {
    return this.http.put<IRestaurante>(this.resourceUrl, restaurante, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRestaurante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRestaurante[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
