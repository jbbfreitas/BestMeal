import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFornecedor } from 'app/shared/model/fornecedor.model';

@Component({
  selector: 'jhi-fornecedor-detail',
  templateUrl: './fornecedor-detail.component.html'
})
export class FornecedorDetailComponent implements OnInit {
  fornecedor: IFornecedor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.fornecedor = fornecedor;
    });
  }

  previousState() {
    window.history.back();
  }
}
