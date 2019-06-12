import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';
import { CartaoRecompensaService } from '../cartao-recompensa/cartao-recompensa.service';

@Component({
  selector: 'jhi-cliente-cartao-recompensa-delete-dialog',
  templateUrl: './cliente-cartao-recompensa-delete-dialog.component.html'
})
export class ClienteCartaoRecompensaDeleteDialogComponent {
  cartaoRecompensa: ICartaoRecompensa;

  constructor(
    protected cartaoRecompensaService: CartaoRecompensaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cartaoRecompensaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cartaoRecompensaListModification',
        content: 'Deleted an cartaoRecompensa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cartao-recompensa-delete-popup',
  template: ''
})
export class ClienteCartaoRecompensaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cartaoRecompensa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteCartaoRecompensaDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.cartaoRecompensa = cartaoRecompensa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoRecompensa.cliente.id + '/cartaoRecompensa' } }]);
            //  this.router.navigate(['/cartao-recompensa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoRecompensa.cliente.id + '/cartaoRecompensa' } }]);
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
