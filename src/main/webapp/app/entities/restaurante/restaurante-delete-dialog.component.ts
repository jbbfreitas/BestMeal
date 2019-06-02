import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRestaurante } from 'app/shared/model/restaurante.model';
import { RestauranteService } from './restaurante.service';

@Component({
  selector: 'jhi-restaurante-delete-dialog',
  templateUrl: './restaurante-delete-dialog.component.html'
})
export class RestauranteDeleteDialogComponent {
  restaurante: IRestaurante;

  constructor(
    protected restauranteService: RestauranteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.restauranteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'restauranteListModification',
        content: 'Deleted an restaurante'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-restaurante-delete-popup',
  template: ''
})
export class RestauranteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ restaurante }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RestauranteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.restaurante = restaurante;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/restaurante', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/restaurante', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
