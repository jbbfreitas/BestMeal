import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFornecedor, Fornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio';
import { PessoaValidityCommonService } from 'app/shared/reuse/pessoa-validity.common.service';

@Component({
  selector: 'jhi-fornecedor-update',
  templateUrl: './fornecedor-update.component.html'
})
export class FornecedorUpdateComponent implements OnInit {
  fornecedor: IFornecedor;
  isSaving: boolean;

  municipios: IMunicipio[];

  editForm = this.pessoaValidityCommonService.createEditForm(this.fb);

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fornecedorService: FornecedorService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected pessoaValidityCommonService: PessoaValidityCommonService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.updateForm(fornecedor);
      this.fornecedor = fornecedor;
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

  updateForm(fornecedor: IFornecedor) {
    this.editForm.patchValue({
      id: fornecedor.id,
      tipo: fornecedor.tipo,
      cpf: fornecedor.cpf,
      cnpj: fornecedor.cnpj,
      primeiroNome: fornecedor.primeiroNome,
      nomeMeio: fornecedor.nomeMeio,
      sobreNome: fornecedor.sobreNome,
      saudacao: fornecedor.saudacao,
      titulo: fornecedor.titulo,
      cep: fornecedor.cep,
      tipoLogradouro: fornecedor.tipoLogradouro,
      nomeLogradouro: fornecedor.nomeLogradouro,
      complemento: fornecedor.complemento,
      municipio: fornecedor.municipio
    });
    this.pessoaValidityCommonService.setPessoa(fornecedor); // Grava o valor de pessoa em service para ser usado na validação
    this.pessoaValidityCommonService.setTipoPessoa('fornecedor'); // Grava o valor de pessoa em service para ser usado na validação
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fornecedor = this.createFromForm();
    if (fornecedor.id !== undefined) {
      this.subscribeToSaveResponse(this.fornecedorService.update(fornecedor));
    } else {
      this.subscribeToSaveResponse(this.fornecedorService.create(fornecedor));
    }
  }

  private createFromForm(): IFornecedor {
    const entity = {
      ...new Fornecedor(),
      id: this.editForm.get(['id']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>) {
    result.subscribe((res: HttpResponse<IFornecedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
