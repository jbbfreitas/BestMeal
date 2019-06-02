import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRestaurante, Restaurante } from 'app/shared/model/restaurante.model';
import { RestauranteService } from './restaurante.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio';

@Component({
  selector: 'jhi-restaurante-update',
  templateUrl: './restaurante-update.component.html'
})
export class RestauranteUpdateComponent implements OnInit {
  restaurante: IRestaurante;
  isSaving: boolean;

  municipios: IMunicipio[];

  editForm = this.fb.group({
    id: [],
    logo: [null, [Validators.required]],
    logoContentType: [],
    tipo: [],
    cpf: [],
    cnpj: [],
    primeiroNome: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
    nomeMeio: [null, [Validators.minLength(2), Validators.maxLength(30)]],
    sobreNome: [null, [Validators.minLength(2), Validators.maxLength(30)]],
    saudacao: [],
    titulo: [null, [Validators.minLength(3), Validators.maxLength(15)]],
    cep: [null, [Validators.pattern('^[0-9]{2}.[0-9]{3}-[0-9]{3}$')]],
    tipoLogradouro: [],
    nomeLogradouro: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    complemento: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(30)]],
    municipio: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected restauranteService: RestauranteService,
    protected municipioService: MunicipioService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ restaurante }) => {
      this.updateForm(restaurante);
      this.restaurante = restaurante;
    });
    this.municipioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe((res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(restaurante: IRestaurante) {
    this.editForm.patchValue({
      id: restaurante.id,
      logo: restaurante.logo,
      logoContentType: restaurante.logoContentType,
      tipo: restaurante.tipo,
      cpf: restaurante.cpf,
      cnpj: restaurante.cnpj,
      primeiroNome: restaurante.primeiroNome,
      nomeMeio: restaurante.nomeMeio,
      sobreNome: restaurante.sobreNome,
      saudacao: restaurante.saudacao,
      titulo: restaurante.titulo,
      cep: restaurante.cep,
      tipoLogradouro: restaurante.tipoLogradouro,
      nomeLogradouro: restaurante.nomeLogradouro,
      complemento: restaurante.complemento,
      municipio: restaurante.municipio
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const restaurante = this.createFromForm();
    if (restaurante.id !== undefined) {
      this.subscribeToSaveResponse(this.restauranteService.update(restaurante));
    } else {
      this.subscribeToSaveResponse(this.restauranteService.create(restaurante));
    }
  }

  private createFromForm(): IRestaurante {
    const entity = {
      ...new Restaurante(),
      id: this.editForm.get(['id']).value,
      logoContentType: this.editForm.get(['logoContentType']).value,
      logo: this.editForm.get(['logo']).value,
      tipo: this.editForm.get(['tipo']).value,
      cpf: this.editForm.get(['cpf']).value,
      cnpj: this.editForm.get(['cnpj']).value,
      primeiroNome: this.editForm.get(['primeiroNome']).value,
      nomeMeio: this.editForm.get(['nomeMeio']).value,
      sobreNome: this.editForm.get(['sobreNome']).value,
      saudacao: this.editForm.get(['saudacao']).value,
      titulo: this.editForm.get(['titulo']).value,
      cep: this.editForm.get(['cep']).value,
      tipoLogradouro: this.editForm.get(['tipoLogradouro']).value,
      nomeLogradouro: this.editForm.get(['nomeLogradouro']).value,
      complemento: this.editForm.get(['complemento']).value,
      municipio: this.editForm.get(['municipio']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurante>>) {
    result.subscribe((res: HttpResponse<IRestaurante>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMunicipioById(index: number, item: IMunicipio) {
    return item.id;
  }
}
