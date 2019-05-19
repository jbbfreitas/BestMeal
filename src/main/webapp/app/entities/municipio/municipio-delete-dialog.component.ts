import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from './municipio.service';

@Component({
  selector: 'jhi-municipio-delete-dialog',
  templateUrl: './municipio-delete-dialog.component.html'
})
export class MunicipioDeleteDialogComponent {
  municipio: IMunicipio;

  constructor(protected municipioService: MunicipioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.municipioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'municipioListModification',
        content: 'Deleted an municipio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-municipio-delete-popup',
  template: ''
})
export class MunicipioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ municipio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MunicipioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.municipio = municipio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/municipio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/municipio', { outlets: { popup: null } }]);
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
