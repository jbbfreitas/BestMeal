import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICliente } from 'app/shared/model/cliente.model';
import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';

type EntityResponseType = HttpResponse<ICliente>;
type EntityArrayResponseType = HttpResponse<ICliente[]>;

@Injectable({ providedIn: 'root' })
export class ClienteService {
  public resourceUrl = SERVER_API_URL + 'api/clientes';

  cliente: ICliente;

  constructor(protected http: HttpClient) {}

  create(cliente: ICliente): Observable<EntityResponseType> {
    return this.http.post<ICliente>(this.resourceUrl, cliente, { observe: 'response' });
  }

  update(cliente: ICliente): Observable<EntityResponseType> {
    return this.http.put<ICliente>(this.resourceUrl, cliente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICliente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICliente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  queryAllCartaoCredito(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICartaoCredito[]>(`${this.resourceUrl}/cartao-creditos`, { params: options, observe: 'response' });
  }

  setCliente(cliente: ICliente) {
    this.cliente = cliente;
  }
  getCliente(): ICliente {
    return this.cliente;
  }
}
