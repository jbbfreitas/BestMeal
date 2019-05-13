/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { CartaoRecompensaDetailComponent } from 'app/entities/cartao-recompensa/cartao-recompensa-detail.component';
import { CartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';

describe('Component Tests', () => {
  describe('CartaoRecompensa Management Detail Component', () => {
    let comp: CartaoRecompensaDetailComponent;
    let fixture: ComponentFixture<CartaoRecompensaDetailComponent>;
    const route = ({ data: of({ cartaoRecompensa: new CartaoRecompensa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [CartaoRecompensaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CartaoRecompensaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartaoRecompensaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cartaoRecompensa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
