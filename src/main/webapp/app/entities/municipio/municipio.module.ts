import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BestMealSharedModule } from 'app/shared';
import {
  MunicipioComponent,
  MunicipioDetailComponent,
  MunicipioUpdateComponent,
  MunicipioDeletePopupComponent,
  MunicipioDeleteDialogComponent,
  municipioRoute,
  municipioPopupRoute
} from './';

const ENTITY_STATES = [...municipioRoute, ...municipioPopupRoute];

@NgModule({
  imports: [BestMealSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MunicipioComponent,
    MunicipioDetailComponent,
    MunicipioUpdateComponent,
    MunicipioDeleteDialogComponent,
    MunicipioDeletePopupComponent
  ],
  entryComponents: [MunicipioComponent, MunicipioUpdateComponent, MunicipioDeleteDialogComponent, MunicipioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BestMealMunicipioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
