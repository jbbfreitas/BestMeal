import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BestMealSharedModule } from 'app/shared';
import {
  ProdutoComponent,
  ProdutoDetailComponent,
  ProdutoUpdateComponent,
  ProdutoDeletePopupComponent,
  ProdutoDeleteDialogComponent,
  produtoRoute,
  produtoPopupRoute
} from './';

const ENTITY_STATES = [...produtoRoute, ...produtoPopupRoute];

@NgModule({
  imports: [BestMealSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProdutoComponent,
    ProdutoDetailComponent,
    ProdutoUpdateComponent,
    ProdutoDeleteDialogComponent,
    ProdutoDeletePopupComponent
  ],
  entryComponents: [ProdutoComponent, ProdutoUpdateComponent, ProdutoDeleteDialogComponent, ProdutoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BestMealProdutoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
