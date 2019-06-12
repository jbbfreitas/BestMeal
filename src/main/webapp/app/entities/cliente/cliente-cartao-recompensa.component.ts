import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CartaoCreditoService } from '../cartao-credito';
import { ClienteService } from './cliente.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ICartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';

@Component({
  selector: 'jhi-cliente-cartao-recompensa',
  templateUrl: './cliente-cartao-recompensa.component.html'
})
export class ClienteCartaoRecompensaComponent implements OnInit, OnDestroy {
  cliente: ICliente;

  currentAccount: any;
  cartaoRecompensas: ICartaoRecompensa[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected cartaoCreditoService: CartaoCreditoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected clienteService: ClienteService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.clienteService
      .queryAllCartaoRecompensa({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        id: this.cliente.id
      })
      .subscribe(
        (res: HttpResponse<ICartaoRecompensa[]>) => this.paginateCartaoRecompensas(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/cliente', this.cliente.id, 'cartaoRecompensa'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/cartao-recompensa',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    /*
    subscreve para o resolve a fim de receber o cliente.
    vide cliente-cartao.componente.html e cliente.route.ts
    */
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.cliente = cliente;
    });

    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCartaoCreditos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICartaoCredito) {
    return item.id;
  }

  registerChangeInCartaoCreditos() {
    this.eventSubscriber = this.eventManager.subscribe('cartaoRecompensaListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCartaoRecompensas(data: ICartaoRecompensa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.cartaoRecompensas = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
