import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPessoa } from 'app/shared/model/pessoa.model';

@Component({
  selector: 'jhi-pessoa-detail',
  templateUrl: './pessoa-detail.component.html'
})
export class PessoaDetailComponent implements OnInit {
  pessoa: IPessoa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      this.pessoa = pessoa;
    });
  }

  previousState() {
    window.history.back();
  }
}
