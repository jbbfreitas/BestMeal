/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { PessoaDetailComponent } from 'app/entities/pessoa/pessoa-detail.component';
import { Pessoa } from 'app/shared/model/pessoa.model';

describe('Component Tests', () => {
  describe('Pessoa Management Detail Component', () => {
    let comp: PessoaDetailComponent;
    let fixture: ComponentFixture<PessoaDetailComponent>;
    const route = ({ data: of({ pessoa: new Pessoa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [PessoaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PessoaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PessoaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pessoa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
