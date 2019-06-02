import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Restaurante } from 'app/shared/model/restaurante.model';
import { RestauranteService } from './restaurante.service';
import { RestauranteComponent } from './restaurante.component';
import { RestauranteDetailComponent } from './restaurante-detail.component';
import { RestauranteUpdateComponent } from './restaurante-update.component';
import { RestauranteDeletePopupComponent } from './restaurante-delete-dialog.component';
import { IRestaurante } from 'app/shared/model/restaurante.model';

@Injectable({ providedIn: 'root' })
export class RestauranteResolve implements Resolve<IRestaurante> {
  constructor(private service: RestauranteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRestaurante> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Restaurante>) => response.ok),
        map((restaurante: HttpResponse<Restaurante>) => restaurante.body)
      );
    }
    return of(new Restaurante());
  }
}

export const restauranteRoute: Routes = [
  {
    path: '',
    component: RestauranteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bestMealApp.restaurante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RestauranteDetailComponent,
    resolve: {
      restaurante: RestauranteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.restaurante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RestauranteUpdateComponent,
    resolve: {
      restaurante: RestauranteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.restaurante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RestauranteUpdateComponent,
    resolve: {
      restaurante: RestauranteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.restaurante.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const restaurantePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RestauranteDeletePopupComponent,
    resolve: {
      restaurante: RestauranteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.restaurante.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
