import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BestMealSharedModule } from 'app/shared';
import {
  RestauranteComponent,
  RestauranteDetailComponent,
  RestauranteUpdateComponent,
  RestauranteDeletePopupComponent,
  RestauranteDeleteDialogComponent,
  restauranteRoute,
  restaurantePopupRoute
} from './';

const ENTITY_STATES = [...restauranteRoute, ...restaurantePopupRoute];

@NgModule({
  imports: [BestMealSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RestauranteComponent,
    RestauranteDetailComponent,
    RestauranteUpdateComponent,
    RestauranteDeleteDialogComponent,
    RestauranteDeletePopupComponent
  ],
  entryComponents: [RestauranteComponent, RestauranteUpdateComponent, RestauranteDeleteDialogComponent, RestauranteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BestMealRestauranteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
