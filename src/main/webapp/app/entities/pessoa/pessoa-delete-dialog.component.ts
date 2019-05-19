import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from './pessoa.service';

@Component({
  selector: 'jhi-pessoa-delete-dialog',
  templateUrl: './pessoa-delete-dialog.component.html'
})
export class PessoaDeleteDialogComponent {
  pessoa: IPessoa;

  constructor(protected pessoaService: PessoaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pessoaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pessoaListModification',
        content: 'Deleted an pessoa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pessoa-delete-popup',
  template: ''
})
export class PessoaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PessoaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pessoa = pessoa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pessoa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pessoa', { outlets: { popup: null } }]);
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
