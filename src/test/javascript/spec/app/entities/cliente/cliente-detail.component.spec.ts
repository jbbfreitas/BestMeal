/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { ClienteDetailComponent } from 'app/entities/cliente/cliente-detail.component';
import { Cliente } from 'app/shared/model/cliente.model';

describe('Component Tests', () => {
  describe('Cliente Management Detail Component', () => {
    let comp: ClienteDetailComponent;
    let fixture: ComponentFixture<ClienteDetailComponent>;
    const route = ({ data: of({ cliente: new Cliente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [ClienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
