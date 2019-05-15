import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';

@Component({
  selector: 'jhi-produto-delete-dialog',
  templateUrl: './produto-delete-dialog.component.html'
})
export class ProdutoDeleteDialogComponent {
  produto: IProduto;

  constructor(protected produtoService: ProdutoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.produtoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'produtoListModification',
        content: 'Deleted an produto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-produto-delete-popup',
  template: ''
})
export class ProdutoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProdutoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.produto = produto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/produto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/produto', { outlets: { popup: null } }]);
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
