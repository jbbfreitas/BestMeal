/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BestMealTestModule } from '../../../test.module';
import { RestauranteDeleteDialogComponent } from 'app/entities/restaurante/restaurante-delete-dialog.component';
import { RestauranteService } from 'app/entities/restaurante/restaurante.service';

describe('Component Tests', () => {
  describe('Restaurante Management Delete Component', () => {
    let comp: RestauranteDeleteDialogComponent;
    let fixture: ComponentFixture<RestauranteDeleteDialogComponent>;
    let service: RestauranteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BestMealTestModule],
        declarations: [RestauranteDeleteDialogComponent]
      })
        .overrideTemplate(RestauranteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RestauranteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RestauranteService);
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
