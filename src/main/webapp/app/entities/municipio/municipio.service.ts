import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMunicipio } from 'app/shared/model/municipio.model';

type EntityResponseType = HttpResponse<IMunicipio>;
type EntityArrayResponseType = HttpResponse<IMunicipio[]>;

@Injectable({ providedIn: 'root' })
export class MunicipioService {
  public resourceUrl = SERVER_API_URL + 'api/municipios';

  constructor(protected http: HttpClient) {}

  create(municipio: IMunicipio): Observable<EntityResponseType> {
    return this.http.post<IMunicipio>(this.resourceUrl, municipio, { observe: 'response' });
  }

  update(municipio: IMunicipio): Observable<EntityResponseType> {
    return this.http.put<IMunicipio>(this.resourceUrl, municipio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMunicipio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMunicipio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
