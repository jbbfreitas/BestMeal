import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';

@Component({
  selector: 'jhi-cartao-credito-detail',
  templateUrl: './cartao-credito-detail.component.html'
})
export class CartaoCreditoDetailComponent implements OnInit {
  cartaoCredito: ICartaoCredito;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cartaoCredito }) => {
      this.cartaoCredito = cartaoCredito;
    });
  }

  previousState() {
    window.history.back();
  }
}
