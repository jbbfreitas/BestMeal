import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMenu, Menu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';

@Component({
  selector: 'jhi-menu-update',
  templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
  menu: IMenu;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    grupo: [],
    valorNormal: [null, [Validators.required, Validators.min(0), Validators.max(20000)]],
    tempoPreparo: [null, [Validators.required, Validators.min(0), Validators.max(60)]],
    isDisponivel: [null, [Validators.required]],
    valorReduzido: [null, [Validators.required, Validators.min(0), Validators.max(2000)]]
  });

  constructor(protected menuService: MenuService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ menu }) => {
      this.updateForm(menu);
      this.menu = menu;
    });
  }

  updateForm(menu: IMenu) {
    this.editForm.patchValue({
      id: menu.id,
      nome: menu.nome,
      grupo: menu.grupo,
      valorNormal: menu.valorNormal,
      tempoPreparo: menu.tempoPreparo,
      isDisponivel: menu.isDisponivel,
      valorReduzido: menu.valorReduzido
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const menu = this.createFromForm();
    if (menu.id !== undefined) {
      this.subscribeToSaveResponse(this.menuService.update(menu));
    } else {
      this.subscribeToSaveResponse(this.menuService.create(menu));
    }
  }

  private createFromForm(): IMenu {
    const entity = {
      ...new Menu(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      grupo: this.editForm.get(['grupo']).value,
      valorNormal: this.editForm.get(['valorNormal']).value,
      tempoPreparo: this.editForm.get(['tempoPreparo']).value,
      isDisponivel: this.editForm.get(['isDisponivel']).value,
      valorReduzido: this.editForm.get(['valorReduzido']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
    result.subscribe((res: HttpResponse<IMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
