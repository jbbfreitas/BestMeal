import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMunicipio } from 'app/shared/model/municipio.model';

@Component({
  selector: 'jhi-municipio-detail',
  templateUrl: './municipio-detail.component.html'
})
export class MunicipioDetailComponent implements OnInit {
  municipio: IMunicipio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ municipio }) => {
      this.municipio = municipio;
    });
  }

  previousState() {
    window.history.back();
  }
}
