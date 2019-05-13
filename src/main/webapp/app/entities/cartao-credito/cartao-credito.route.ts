import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CartaoCredito } from 'app/shared/model/cartao-credito.model';
import { CartaoCreditoService } from './cartao-credito.service';
import { CartaoCreditoComponent } from './cartao-credito.component';
import { CartaoCreditoDetailComponent } from './cartao-credito-detail.component';
import { CartaoCreditoUpdateComponent } from './cartao-credito-update.component';
import { CartaoCreditoDeletePopupComponent } from './cartao-credito-delete-dialog.component';
import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';

@Injectable({ providedIn: 'root' })
export class CartaoCreditoResolve implements Resolve<ICartaoCredito> {
  constructor(private service: CartaoCreditoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICartaoCredito> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CartaoCredito>) => response.ok),
        map((cartaoCredito: HttpResponse<CartaoCredito>) => cartaoCredito.body)
      );
    }
    return of(new CartaoCredito());
  }
}

export const cartaoCreditoRoute: Routes = [
  {
    path: '',
    component: CartaoCreditoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bestMealApp.cartaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CartaoCreditoDetailComponent,
    resolve: {
      cartaoCredito: CartaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CartaoCreditoUpdateComponent,
    resolve: {
      cartaoCredito: CartaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CartaoCreditoUpdateComponent,
    resolve: {
      cartaoCredito: CartaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cartaoCreditoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CartaoCreditoDeletePopupComponent,
    resolve: {
      cartaoCredito: CartaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
