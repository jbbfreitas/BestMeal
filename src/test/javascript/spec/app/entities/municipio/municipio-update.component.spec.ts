/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { MunicipioUpdateComponent } from 'app/entities/municipio/municipio-update.component';
import { MunicipioService } from 'app/entities/municipio/municipio.service';
import { Municipio } from 'app/shared/model/municipio.model';

describe('Component Tests', () => {
  describe('Municipio Management Update Component', () => {
    let comp: MunicipioUpdateComponent;
    let fixture: ComponentFixture<MunicipioUpdateComponent>;
    let service: MunicipioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [MunicipioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MunicipioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MunicipioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MunicipioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Municipio(123);
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
        const entity = new Municipio();
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
