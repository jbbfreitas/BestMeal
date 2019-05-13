/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BestMealTestModule } from '../../../test.module';
import { CartaoRecompensaDeleteDialogComponent } from 'app/entities/cartao-recompensa/cartao-recompensa-delete-dialog.component';
import { CartaoRecompensaService } from 'app/entities/cartao-recompensa/cartao-recompensa.service';

describe('Component Tests', () => {
  describe('CartaoRecompensa Management Delete Component', () => {
    let comp: CartaoRecompensaDeleteDialogComponent;
    let fixture: ComponentFixture<CartaoRecompensaDeleteDialogComponent>;
    let service: CartaoRecompensaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [CartaoRecompensaDeleteDialogComponent]
      })
        .overrideTemplate(CartaoRecompensaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartaoRecompensaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartaoRecompensaService);
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
