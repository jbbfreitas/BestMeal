import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BestMealSharedLibsModule, BestMealSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BestMealSharedLibsModule, BestMealSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BestMealSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BestMealSharedModule {
  static forRoot() {
    return {
      ngModule: BestMealSharedModule
    };
  }
}
