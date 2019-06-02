/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { RestauranteDetailComponent } from 'app/entities/restaurante/restaurante-detail.component';
import { Restaurante } from 'app/shared/model/restaurante.model';

describe('Component Tests', () => {
  describe('Restaurante Management Detail Component', () => {
    let comp: RestauranteDetailComponent;
    let fixture: ComponentFixture<RestauranteDetailComponent>;
    const route = ({ data: of({ restaurante: new Restaurante(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [RestauranteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RestauranteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RestauranteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.restaurante).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
