import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';
import { CartaoCreditoService } from '../cartao-credito/cartao-credito.service';

@Component({
  selector: 'jhi-cliente-cartao-credito-delete-dialog',
  templateUrl: './cliente-cartao-credito-delete-dialog.component.html'
})
export class ClienteCartaoCreditoDeleteDialogComponent {
  cartaoCredito: ICartaoCredito;

  constructor(
    protected cartaoCreditoService: CartaoCreditoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cartaoCreditoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cartaoCreditoListModification',
        content: 'Deleted an cartaoCredito'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cartao-credito-delete-popup',
  template: ''
})
export class ClienteCartaoCreditoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cartaoCredito }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteCartaoCreditoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.cartaoCredito = cartaoCredito;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoCredito.cliente.id + '/cartao' } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoCredito.cliente.id + '/cartao' } }]);
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
