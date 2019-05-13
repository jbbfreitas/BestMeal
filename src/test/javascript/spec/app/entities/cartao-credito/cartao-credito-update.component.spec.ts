/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { CartaoCreditoUpdateComponent } from 'app/entities/cartao-credito/cartao-credito-update.component';
import { CartaoCreditoService } from 'app/entities/cartao-credito/cartao-credito.service';
import { CartaoCredito } from 'app/shared/model/cartao-credito.model';

describe('Component Tests', () => {
  describe('CartaoCredito Management Update Component', () => {
    let comp: CartaoCreditoUpdateComponent;
    let fixture: ComponentFixture<CartaoCreditoUpdateComponent>;
    let service: CartaoCreditoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [CartaoCreditoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CartaoCreditoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartaoCreditoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartaoCreditoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CartaoCredito(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CartaoCredito();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
