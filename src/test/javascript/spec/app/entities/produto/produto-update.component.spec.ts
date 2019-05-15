/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { ProdutoUpdateComponent } from 'app/entities/produto/produto-update.component';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { Produto } from 'app/shared/model/produto.model';

describe('Component Tests', () => {
  describe('Produto Management Update Component', () => {
    let comp: ProdutoUpdateComponent;
    let fixture: ComponentFixture<ProdutoUpdateComponent>;
    let service: ProdutoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [ProdutoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProdutoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdutoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdutoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Produto(123);
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
        const entity = new Produto();
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
