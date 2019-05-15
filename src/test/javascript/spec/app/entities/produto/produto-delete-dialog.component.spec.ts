/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BestMealTestModule } from '../../../test.module';
import { ProdutoDeleteDialogComponent } from 'app/entities/produto/produto-delete-dialog.component';
import { ProdutoService } from 'app/entities/produto/produto.service';

describe('Component Tests', () => {
  describe('Produto Management Delete Component', () => {
    let comp: ProdutoDeleteDialogComponent;
    let fixture: ComponentFixture<ProdutoDeleteDialogComponent>;
    let service: ProdutoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [ProdutoDeleteDialogComponent]
      })
        .overrideTemplate(ProdutoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProdutoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdutoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
