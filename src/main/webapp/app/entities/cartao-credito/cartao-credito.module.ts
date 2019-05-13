import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BestMealSharedModule } from 'app/shared';
import {
  CartaoCreditoComponent,
  CartaoCreditoDetailComponent,
  CartaoCreditoUpdateComponent,
  CartaoCreditoDeletePopupComponent,
  CartaoCreditoDeleteDialogComponent,
  cartaoCreditoRoute,
  cartaoCreditoPopupRoute
} from './';

const ENTITY_STATES = [...cartaoCreditoRoute, ...cartaoCreditoPopupRoute];

@NgModule({
  imports: [BestMealSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CartaoCreditoComponent,
    CartaoCreditoDetailComponent,
    CartaoCreditoUpdateComponent,
    CartaoCreditoDeleteDialogComponent,
    CartaoCreditoDeletePopupComponent
  ],
  entryComponents: [
    CartaoCreditoComponent,
    CartaoCreditoUpdateComponent,
    CartaoCreditoDeleteDialogComponent,
    CartaoCreditoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BestMealCartaoCreditoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
