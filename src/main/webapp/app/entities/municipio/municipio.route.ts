import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Municipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from './municipio.service';
import { MunicipioComponent } from './municipio.component';
import { MunicipioDetailComponent } from './municipio-detail.component';
import { MunicipioUpdateComponent } from './municipio-update.component';
import { MunicipioDeletePopupComponent } from './municipio-delete-dialog.component';
import { IMunicipio } from 'app/shared/model/municipio.model';

@Injectable({ providedIn: 'root' })
export class MunicipioResolve implements Resolve<IMunicipio> {
  constructor(private service: MunicipioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMunicipio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Municipio>) => response.ok),
        map((municipio: HttpResponse<Municipio>) => municipio.body)
      );
    }
    return of(new Municipio());
  }
}

export const municipioRoute: Routes = [
  {
    path: '',
    component: MunicipioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bestMealApp.municipio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MunicipioDetailComponent,
    resolve: {
      municipio: MunicipioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.municipio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MunicipioUpdateComponent,
    resolve: {
      municipio: MunicipioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.municipio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MunicipioUpdateComponent,
    resolve: {
      municipio: MunicipioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.municipio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const municipioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MunicipioDeletePopupComponent,
    resolve: {
      municipio: MunicipioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.municipio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
