import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';
import { CartaoRecompensaService } from './cartao-recompensa.service';
import { CartaoRecompensaComponent } from './cartao-recompensa.component';
import { CartaoRecompensaDetailComponent } from './cartao-recompensa-detail.component';
import { CartaoRecompensaUpdateComponent } from './cartao-recompensa-update.component';
import { CartaoRecompensaDeletePopupComponent } from './cartao-recompensa-delete-dialog.component';
import { ICartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';

@Injectable({ providedIn: 'root' })
export class CartaoRecompensaResolve implements Resolve<ICartaoRecompensa> {
  constructor(private service: CartaoRecompensaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICartaoRecompensa> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CartaoRecompensa>) => response.ok),
        map((cartaoRecompensa: HttpResponse<CartaoRecompensa>) => cartaoRecompensa.body)
      );
    }
    return of(new CartaoRecompensa());
  }
}

export const cartaoRecompensaRoute: Routes = [
  {
    path: '',
    component: CartaoRecompensaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bestMealApp.cartaoRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CartaoRecompensaDetailComponent,
    resolve: {
      cartaoRecompensa: CartaoRecompensaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CartaoRecompensaUpdateComponent,
    resolve: {
      cartaoRecompensa: CartaoRecompensaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CartaoRecompensaUpdateComponent,
    resolve: {
      cartaoRecompensa: CartaoRecompensaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cartaoRecompensaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CartaoRecompensaDeletePopupComponent,
    resolve: {
      cartaoRecompensa: CartaoRecompensaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
