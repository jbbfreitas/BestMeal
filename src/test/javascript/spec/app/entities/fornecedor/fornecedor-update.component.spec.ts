/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { FornecedorUpdateComponent } from 'app/entities/fornecedor/fornecedor-update.component';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';
import { Fornecedor } from 'app/shared/model/fornecedor.model';

describe('Component Tests', () => {
  describe('Fornecedor Management Update Component', () => {
    let comp: FornecedorUpdateComponent;
    let fixture: ComponentFixture<FornecedorUpdateComponent>;
    let service: FornecedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [FornecedorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FornecedorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FornecedorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FornecedorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fornecedor(123);
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
        const entity = new Fornecedor();
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
