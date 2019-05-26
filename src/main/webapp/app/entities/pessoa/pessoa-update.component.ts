import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPessoa, Pessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from './pessoa.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio';
import { CustomCPFCNPJValidatorService } from 'app/shared/validators/cpf-cnpj-validators.service';
type EntityArrayResponseType = HttpResponse<IPessoa[]>;

@Component({
  selector: 'jhi-pessoa-update',
  templateUrl: './pessoa-update.component.html'
})
export class PessoaUpdateComponent implements OnInit {
  pessoa: IPessoa;
  isSaving: boolean;
  idPessoa = '0';
  municipios: IMunicipio[];

  editForm = this.fb.group({
    id: [],
    tipo: [],
    cpf: [
      null,
      [Validators.pattern('^[0-9]{3}.?[0-9]{3}.?[0-9]{3}-?[0-9]{2}$'), this.customCPFCNPJValidatorService.isValidCpf()],
      [this.customCPFCNPJValidatorService.existingCpfValidator(this.pessoaService)] // Validação assincrona do cpf
    ],
    cnpj: [
      null,
      [this.customCPFCNPJValidatorService.isValidCnpj(), Validators.pattern('^[0-9]{2}.?[0-9]{3}.?[0-9]{3}/?[0-9]{4}-?[0-9]{2}$')]
    ],
    primeiroNome: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
    nomeMeio: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
    sobreNome: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
    saudacao: [],
    titulo: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
    cep: [null, [Validators.pattern('^[0-9]{2}.[0-9]{3}-[0-9]{3}$')]],
    tipoLogradouro: [],
    nomeLogradouro: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    complemento: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(30)]],
    municipio: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pessoaService: PessoaService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected customCPFCNPJValidatorService: CustomCPFCNPJValidatorService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      this.updateForm(pessoa);
      this.pessoa = pessoa;
      this.idPessoa = String(this.pessoa.id);
    });

    this.municipioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe((res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pessoa: IPessoa) {
    this.editForm.patchValue({
      id: pessoa.id,
      tipo: pessoa.tipo,
      cpf: pessoa.cpf,
      cnpj: pessoa.cnpj,
      primeiroNome: pessoa.primeiroNome,
      nomeMeio: pessoa.nomeMeio,
      sobreNome: pessoa.sobreNome,
      saudacao: pessoa.saudacao,
      titulo: pessoa.titulo,
      cep: pessoa.cep,
      tipoLogradouro: pessoa.tipoLogradouro,
      nomeLogradouro: pessoa.nomeLogradouro,
      complemento: pessoa.complemento,
      municipio: pessoa.municipio
    });
    this.pessoaService.setPessoa(pessoa); //Grava o valor de pessoa em service para ser usado na validação
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pessoa = this.createFromForm();
    if (pessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaService.update(pessoa));
    } else {
      this.subscribeToSaveResponse(this.pessoaService.create(pessoa));
    }
  }

  private createFromForm(): IPessoa {
    const entity = {
      ...new Pessoa(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>) {
    result.subscribe((res: HttpResponse<IPessoa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
