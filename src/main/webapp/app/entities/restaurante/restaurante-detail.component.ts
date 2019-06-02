import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRestaurante } from 'app/shared/model/restaurante.model';

@Component({
  selector: 'jhi-restaurante-detail',
  templateUrl: './restaurante-detail.component.html'
})
export class RestauranteDetailComponent implements OnInit {
  restaurante: IRestaurante;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ restaurante }) => {
      this.restaurante = restaurante;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
