/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BestMealTestModule } from '../../../test.module';
import { CartaoCreditoDeleteDialogComponent } from 'app/entities/cartao-credito/cartao-credito-delete-dialog.component';
import { CartaoCreditoService } from 'app/entities/cartao-credito/cartao-credito.service';

describe('Component Tests', () => {
  describe('CartaoCredito Management Delete Component', () => {
    let comp: CartaoCreditoDeleteDialogComponent;
    let fixture: ComponentFixture<CartaoCreditoDeleteDialogComponent>;
    let service: CartaoCreditoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [CartaoCreditoDeleteDialogComponent]
      })
        .overrideTemplate(CartaoCreditoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartaoCreditoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartaoCreditoService);
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
