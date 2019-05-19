import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMunicipio, Municipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from './municipio.service';

@Component({
  selector: 'jhi-municipio-update',
  templateUrl: './municipio-update.component.html'
})
export class MunicipioUpdateComponent implements OnInit {
  municipio: IMunicipio;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nomeMunicipio: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    uf: []
  });

  constructor(protected municipioService: MunicipioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ municipio }) => {
      this.updateForm(municipio);
      this.municipio = municipio;
    });
  }

  updateForm(municipio: IMunicipio) {
    this.editForm.patchValue({
      id: municipio.id,
      nomeMunicipio: municipio.nomeMunicipio,
      uf: municipio.uf
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const municipio = this.createFromForm();
    if (municipio.id !== undefined) {
      this.subscribeToSaveResponse(this.municipioService.update(municipio));
    } else {
      this.subscribeToSaveResponse(this.municipioService.create(municipio));
    }
  }

  private createFromForm(): IMunicipio {
    const entity = {
      ...new Municipio(),
      id: this.editForm.get(['id']).value,
      nomeMunicipio: this.editForm.get(['nomeMunicipio']).value,
      uf: this.editForm.get(['uf']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipio>>) {
    result.subscribe((res: HttpResponse<IMunicipio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
