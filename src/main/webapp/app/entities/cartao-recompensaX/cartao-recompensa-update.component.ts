import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICartaoRecompensa, CartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';
import { CartaoRecompensaService } from './cartao-recompensa.service';

@Component({
  selector: 'jhi-cartao-recompensa-update',
  templateUrl: './cartao-recompensa-update.component.html'
})
export class CartaoRecompensaUpdateComponent implements OnInit {
  cartaoRecompensa: ICartaoRecompensa;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nomeCartao: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(40)]],
    numero: [null, [Validators.required]],
    validade: [null, [Validators.required, Validators.pattern('^(0[1-9]|1[0-2])/?([0-9]{4}|[0-9]{2})$')]],
    pontuacao: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    situacao: []
  });

  constructor(
    protected cartaoRecompensaService: CartaoRecompensaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cartaoRecompensa }) => {
      this.updateForm(cartaoRecompensa);
      this.cartaoRecompensa = cartaoRecompensa;
    });
  }

  updateForm(cartaoRecompensa: ICartaoRecompensa) {
    this.editForm.patchValue({
      id: cartaoRecompensa.id,
      nomeCartao: cartaoRecompensa.nomeCartao,
      numero: cartaoRecompensa.numero,
      validade: cartaoRecompensa.validade,
      pontuacao: cartaoRecompensa.pontuacao,
      situacao: cartaoRecompensa.situacao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cartaoRecompensa = this.createFromForm();
    if (cartaoRecompensa.id !== undefined) {
      this.subscribeToSaveResponse(this.cartaoRecompensaService.update(cartaoRecompensa));
    } else {
      this.subscribeToSaveResponse(this.cartaoRecompensaService.create(cartaoRecompensa));
    }
  }

  private createFromForm(): ICartaoRecompensa {
    const entity = {
      ...new CartaoRecompensa(),
      id: this.editForm.get(['id']).value,
      nomeCartao: this.editForm.get(['nomeCartao']).value,
      numero: this.editForm.get(['numero']).value,
      validade: this.editForm.get(['validade']).value,
      pontuacao: this.editForm.get(['pontuacao']).value,
      situacao: this.editForm.get(['situacao']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICartaoRecompensa>>) {
    result.subscribe((res: HttpResponse<ICartaoRecompensa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
