/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { CartaoCreditoDetailComponent } from 'app/entities/cartao-credito/cartao-credito-detail.component';
import { CartaoCredito } from 'app/shared/model/cartao-credito.model';

describe('Component Tests', () => {
  describe('CartaoCredito Management Detail Component', () => {
    let comp: CartaoCreditoDetailComponent;
    let fixture: ComponentFixture<CartaoCreditoDetailComponent>;
    const route = ({ data: of({ cartaoCredito: new CartaoCredito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [CartaoCreditoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CartaoCreditoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartaoCreditoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cartaoCredito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
