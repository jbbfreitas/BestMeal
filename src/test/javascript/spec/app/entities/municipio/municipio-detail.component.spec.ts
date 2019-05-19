/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { MunicipioDetailComponent } from 'app/entities/municipio/municipio-detail.component';
import { Municipio } from 'app/shared/model/municipio.model';

describe('Component Tests', () => {
  describe('Municipio Management Detail Component', () => {
    let comp: MunicipioDetailComponent;
    let fixture: ComponentFixture<MunicipioDetailComponent>;
    const route = ({ data: of({ municipio: new Municipio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [MunicipioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MunicipioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MunicipioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.municipio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
