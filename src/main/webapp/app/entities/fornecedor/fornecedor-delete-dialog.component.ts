import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';

@Component({
  selector: 'jhi-fornecedor-delete-dialog',
  templateUrl: './fornecedor-delete-dialog.component.html'
})
export class FornecedorDeleteDialogComponent {
  fornecedor: IFornecedor;

  constructor(
    protected fornecedorService: FornecedorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fornecedorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fornecedorListModification',
        content: 'Deleted an fornecedor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fornecedor-delete-popup',
  template: ''
})
export class FornecedorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FornecedorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fornecedor = fornecedor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fornecedor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fornecedor', { outlets: { popup: null } }]);
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
