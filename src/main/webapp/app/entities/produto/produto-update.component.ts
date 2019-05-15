import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IProduto, Produto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';

@Component({
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html'
})
export class ProdutoUpdateComponent implements OnInit {
  produto: IProduto;
  isSaving: boolean;
  dataUltimaCompraDp: any;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
    nome: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    unidade: [null, [Validators.required]],
    estoqueMinimo: [null, [Validators.required]],
    estoqueAtual: [],
    dataUltimaCompra: [],
    valorUltimaCompra: []
  });

  constructor(protected produtoService: ProdutoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.updateForm(produto);
      this.produto = produto;
    });
  }

  updateForm(produto: IProduto) {
    this.editForm.patchValue({
      id: produto.id,
      codigo: produto.codigo,
      nome: produto.nome,
      unidade: produto.unidade,
      estoqueMinimo: produto.estoqueMinimo,
      estoqueAtual: produto.estoqueAtual,
      dataUltimaCompra: produto.dataUltimaCompra,
      valorUltimaCompra: produto.valorUltimaCompra
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const produto = this.createFromForm();
    if (produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  private createFromForm(): IProduto {
    const entity = {
      ...new Produto(),
      id: this.editForm.get(['id']).value,
      codigo: this.editForm.get(['codigo']).value,
      nome: this.editForm.get(['nome']).value,
      unidade: this.editForm.get(['unidade']).value,
      estoqueMinimo: this.editForm.get(['estoqueMinimo']).value,
      estoqueAtual: this.editForm.get(['estoqueAtual']).value,
      dataUltimaCompra: this.editForm.get(['dataUltimaCompra']).value,
      valorUltimaCompra: this.editForm.get(['valorUltimaCompra']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>) {
    result.subscribe((res: HttpResponse<IProduto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
