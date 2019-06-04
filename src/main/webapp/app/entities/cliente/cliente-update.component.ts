import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  cliente: ICliente;
  isSaving: boolean;

  municipios: IMunicipio[];

  editForm = this.fb.group({
    id: [],
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
    protected jhiAlertService: JhiAlertService,
    protected clienteService: ClienteService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
      this.cliente = cliente;
    });
    this.municipioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe((res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cliente: ICliente) {
    this.editForm.patchValue({
      id: cliente.id,
      tipo: cliente.tipo,
      cpf: cliente.cpf,
      cnpj: cliente.cnpj,
      primeiroNome: cliente.primeiroNome,
      nomeMeio: cliente.nomeMeio,
      sobreNome: cliente.sobreNome,
      saudacao: cliente.saudacao,
      titulo: cliente.titulo,
      cep: cliente.cep,
      tipoLogradouro: cliente.tipoLogradouro,
      nomeLogradouro: cliente.nomeLogradouro,
      complemento: cliente.complemento,
      municipio: cliente.municipio
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    const entity = {
      ...new Cliente(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
