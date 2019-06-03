import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRestaurante, Restaurante } from 'app/shared/model/restaurante.model';
import { RestauranteService } from './restaurante.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio';
import { PessoaValidityCommonService } from 'app/shared/reuse/pessoa-validity.common.service';

@Component({
  selector: 'jhi-restaurante-update',
  templateUrl: './restaurante-update.component.html'
})
export class RestauranteUpdateComponent implements OnInit {
  restaurante: IRestaurante;
  isSaving: boolean;

  municipios: IMunicipio[];
  editForm: FormGroup; // Só declarar

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected restauranteService: RestauranteService,
    protected municipioService: MunicipioService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected pessoaValidityCommonService: PessoaValidityCommonService
  ) {
    this.editForm = this.pessoaValidityCommonService.createEditForm(this.fb); // criar
    this.editForm.addControl('logo', new FormControl('', Validators.required)); // adicionar
    this.editForm.addControl('logoContentType', new FormControl('', Validators.required)); // adicionar
  }

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
    this.editForm = this.pessoaValidityCommonService.setPessoaReValidity(this.editForm);
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
    this.pessoaValidityCommonService.setPessoa(restaurante); // Grava o valor de restaurante em service para ser usado na validação
    this.pessoaValidityCommonService.setTipoPessoa('restaurante'); // Grava o valor de restaurante em service para ser usado na validação
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
