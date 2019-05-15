import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProduto } from 'app/shared/model/produto.model';

@Component({
  selector: 'jhi-produto-detail',
  templateUrl: './produto-detail.component.html'
})
export class ProdutoDetailComponent implements OnInit {
  produto: IProduto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.produto = produto;
    });
  }

  previousState() {
    window.history.back();
  }
}
