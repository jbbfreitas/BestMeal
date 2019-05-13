import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';

@Component({
  selector: 'jhi-cartao-recompensa-detail',
  templateUrl: './cartao-recompensa-detail.component.html'
})
export class CartaoRecompensaDetailComponent implements OnInit {
  cartaoRecompensa: ICartaoRecompensa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cartaoRecompensa }) => {
      this.cartaoRecompensa = cartaoRecompensa;
    });
  }

  previousState() {
    window.history.back();
  }
}
