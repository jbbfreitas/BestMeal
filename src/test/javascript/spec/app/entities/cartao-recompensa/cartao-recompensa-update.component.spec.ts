/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { CartaoRecompensaUpdateComponent } from 'app/entities/cartao-recompensa/cartao-recompensa-update.component';
import { CartaoRecompensaService } from 'app/entities/cartao-recompensa/cartao-recompensa.service';
import { CartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';

describe('Component Tests', () => {
  describe('CartaoRecompensa Management Update Component', () => {
    let comp: CartaoRecompensaUpdateComponent;
    let fixture: ComponentFixture<CartaoRecompensaUpdateComponent>;
    let service: CartaoRecompensaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [CartaoRecompensaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CartaoRecompensaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartaoRecompensaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartaoRecompensaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CartaoRecompensa(123);
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
        const entity = new CartaoRecompensa();
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
