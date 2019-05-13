/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BestMealTestModule } from '../../../test.module';
import { MenuUpdateComponent } from 'app/entities/menu/menu-update.component';
import { MenuService } from 'app/entities/menu/menu.service';
import { Menu } from 'app/shared/model/menu.model';

describe('Component Tests', () => {
  describe('Menu Management Update Component', () => {
    let comp: MenuUpdateComponent;
    let fixture: ComponentFixture<MenuUpdateComponent>;
    let service: MenuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [MenuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MenuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MenuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MenuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Menu(123);
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
        const entity = new Menu();
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
