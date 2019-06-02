/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { RestauranteUpdateComponent } from 'app/entities/restaurante/restaurante-update.component';
import { RestauranteService } from 'app/entities/restaurante/restaurante.service';
import { Restaurante } from 'app/shared/model/restaurante.model';

describe('Component Tests', () => {
  describe('Restaurante Management Update Component', () => {
    let comp: RestauranteUpdateComponent;
    let fixture: ComponentFixture<RestauranteUpdateComponent>;
    let service: RestauranteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [RestauranteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RestauranteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RestauranteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RestauranteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Restaurante(123);
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
        const entity = new Restaurante();
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
