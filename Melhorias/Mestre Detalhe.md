# BestMeal - Formulário Mestre - Detalhe

## O que são formulários mestre-detalhe?

>São formulários que contém uma instância de uma classe principal (por exemplo `Cliente`) e da qual dependem uma ou mais classes (por exemplo `CartãoCredito`). Se pensarmos em um modelo de classes da UML, podederíamos dizer que são a representação dos relacionamentos 1:N.

>No nosso estudo de caso queremos que, ao selecionar um Cliente, sejam exibidos os seus Cartões de Créditos (um ou mais) bem como os seus Cartões Recompensa.

### Implementando um formulário Mestre-Detalhe

> A fim de manter o padrão de desenvolvimento que estamos adotando até o momento, vamos adotar a seguinte estratégia:

- Criar uma entidade `Cliente`
- Criar uma entidade `CartaoCredito` com um relacionamento `N:1` com `Cliente`.
- Implementar as mudanças para que sejam exibidos apenas os cartões do cliente selecionado.

#### Implementações do lado cliente

::: :walking: Passo a passo :::

1. Gerar a entidade Cliente usando o arquivo da Listagem 1

```json
{
    "fluentMethods": true,
    "clientRootFolder": "",
    "relationships": [
        {
            "relationshipName": "municipio",
            "otherEntityName": "municipio",
            "relationshipType": "many-to-one",
            "otherEntityField": "nomeMunicipio"
        }
    ],
    "fields": [
        {
            "fieldName": "tipo",
            "fieldType": "TipoPessoa",
            "fieldValues": "FISICA,JURIDICA"
        },
        {
            "fieldName": "cpf",
            "fieldType": "String"
        },
        {
            "fieldName": "cnpj",
            "fieldType": "String"
        },
        {
            "fieldName": "primeiroNome",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "2",
            "fieldValidateRulesMaxlength": 20
        },
        {
            "fieldName": "nomeMeio",
            "fieldType": "String",
            "fieldValidateRules": [
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "2",
            "fieldValidateRulesMaxlength": "30"
        },
        {
            "fieldName": "sobreNome",
            "fieldType": "String",
            "fieldValidateRules": [
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "2",
            "fieldValidateRulesMaxlength": "30"
        },
        {
            "fieldName": "saudacao",
            "fieldType": "String"
        },
        {
            "fieldName": "titulo",
            "fieldType": "String",
            "fieldValidateRules": [
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "3",
            "fieldValidateRulesMaxlength": "15"
        },
        {
            "fieldName": "cep",
            "fieldType": "String",
            "fieldValidateRules": [
                "pattern"
            ],
            "fieldValidateRulesPattern": "^[0-9]{2}.[0-9]{3}-[0-9]{3}$"
        },
        {
            "fieldName": "tipoLogradouro",
            "fieldType": "TipoLogradouro",
            "fieldValues": "RUA,AVENIDA,TRAVESSA,QUADRA,BECO,ESTRADA,CHACARA,RODOVIA,VIADUTO,SITIO,FEIRA,SETOR,MORRO,LARGO,FAZENDA"
        },
        {
            "fieldName": "nomeLogradouro",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "5",
            "fieldValidateRulesMaxlength": "100"
        },
        {
            "fieldName": "complemento",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "0",
            "fieldValidateRulesMaxlength": "30"
        }
    ],
    "changelogDate": "20190603205331",
    "dto": "no",
    "searchEngine": false,
    "service": "serviceImpl",
    "entityTableName": "cliente",
    "databaseType": "sql",
    "jpaMetamodelFiltering": false,
    "pagination": "pagination"
}

```
<p align="center">
   <strong>Listagem 1 - Arquivo Cliente.json </strong> 
</p>

2. Para que o jhipster gere essa entidade, execute a seguinte instrução no prompt de comandos :

```prompt
jhipster entity Cliente
```

3. Gerar a entidade CartaoCredito usando o arquivo da Listagem 2.

>Calma! Já sei que você vai dizer, 'nós já geramos a entidade CartaoCredito!'. Essa é justamente a beleza do jhipster: poder regerar e melhorar!

```json
{
    "fluentMethods": true,
    "clientRootFolder": "",
    "relationships": [
            {
            "relationshipName": "cliente",
            "otherEntityName": "Cliente",
            "relationshipType": "many-to-one",
            "otherEntityField": "primeiroNome"
        }
    ],
    "fields": [
        {
            "fieldName": "nomeCartao",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "10",
            "fieldValidateRulesMaxlength": "40"
        },
        {
            "fieldName": "bandeira",
            "fieldType": "Bandeira",
            "fieldValues": "AMERICAN,DINERS,ELO,MASTER,VISA"
        },
        {
            "fieldName": "numero",
            "fieldType": "String",
            "fieldValidateRules": [
                "pattern"
            ],
            "fieldValidateRulesPattern": "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$"
        },
        {
            "fieldName": "cvv",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "pattern"
            ],
            "fieldValidateRulesPattern": "^[0-9]{3,4}$"
        },
        {
            "fieldName": "validade",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "pattern"
            ],
            "fieldValidateRulesPattern": "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$"
        }
    ],
    "changelogDate": "20190513200539",
    "dto": "no",
    "searchEngine": false,
    "service": "serviceImpl",
    "entityTableName": "cartao_credito",
    "databaseType": "sql",
    "jpaMetamodelFiltering": false,
    "pagination": "pagination"
}

```
<p align="center">
   <strong>Listagem 2 - Arquivo CartaoCredito.json </strong> 
</p>

::: :pushpin: Importante :::
 
>Observe que estamos fazendo um relacionamento N:1 com a entidade Cliente
```json
    "relationships": [
            {
            "relationshipName": "cliente",
            "otherEntityName": "Cliente",
            "relationshipType": "many-to-one",
            "otherEntityField": "primeiroNome"
        }
    ],

```

4. Gerar a entidade `CartaoRecompensa` usando o arquivo da Listagem 3.

```json
{
    "fluentMethods": true,
    "clientRootFolder": "",
    "relationships": [
            {
            "relationshipName": "cliente",
            "otherEntityName": "Cliente",
            "relationshipType": "many-to-one",
            "otherEntityField": "primeiroNome"
        }
    ],    "fields": [
        {
            "fieldName": "nomeCartao",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "minlength",
                "maxlength"
            ],
            "fieldValidateRulesMinlength": "10",
            "fieldValidateRulesMaxlength": "40"
        },
        {
            "fieldName": "numero",
            "fieldType": "String",
            "fieldValidateRules": [
                "required"
            ]
        },
        {
            "fieldName": "validade",
            "fieldType": "String",
            "fieldValidateRules": [
                "required",
                "pattern"
            ],
            "fieldValidateRulesPattern": "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$"
        },
        {
            "fieldName": "pontuacao",
            "fieldType": "Integer",
            "fieldValidateRules": [
                "required",
                "min",
                "max"
            ],
            "fieldValidateRulesMin": "0",
            "fieldValidateRulesMax": 100
        },
        {
            "fieldName": "situacao",
            "fieldType": "SituacaoCartao",
            "fieldValues": "ATIVO,INATIVO,CANCELADO,PENDENTE"
        }
    ],
    "changelogDate": "20190513203550",
    "dto": "no",
    "searchEngine": false,
    "service": "serviceImpl",
    "entityTableName": "cartao_recompensa",
    "databaseType": "sql",
    "jpaMetamodelFiltering": false,
    "pagination": "pagination"
}

```

<p align="center">
   <strong>Listagem 3 - Arquivo CartaoRecompensa.json </strong> 
</p>


5. Faça as seguintes alterações na view `cliente-update.component.html`, conforme a Listagem 4  

```typescript
<!-- Incluir as duas linhas abaixo -->
            <div class="form-group col-md-6"> 
            </div> 
        </form>
<!--Inicio inclusão-->        
        <div>
            <div>
                <small class="form-text text-primary" jhiTranslate="bestMealApp.pessoa.gerenciarCartoes">
                    Gerenciamento de Cartões:
                </small>
                <hr class="half-rule" />
            </div>
            <button type="button" [routerLink]="['/', 'cliente', { outlets: { popup: cliente.id + '/cartao'} }]"
                replaceUrl="true" queryParamsHandling="merge" class="btn btn-info">
                <fa-icon [icon]="'credit-card'"></fa-icon>&nbsp;<span
                    jhiTranslate="entity.action.managecredit">C. Credito</span>
            </button>
            <button type="button"
                [routerLink]="['/', 'cliente', { outlets: { popup: cliente.id + '/cartaoRecompensa'} }]"
                replaceUrl="true" queryParamsHandling="merge" class="btn btn-info">
                <fa-icon [icon]="'money-check'"></fa-icon>&nbsp;<span
                    jhiTranslate="entity.action.managereward">C. Recompensa</span>
            </button>
        </div>
<!--Final inclusão -->      
    <hr>
    </div>
</div>
```

<p align="center">
   <strong>Listagem 4 - cartao-credito-update.component.html </strong> 
</p>

::: :pushpin: Importante :::
 
>O trecho de código da Listagem 4 tem, basicamente, a função de acrescentar dois botões: o primeiro para mostrar
os cartões de crédito daquele cliente e o segundo para mostrar os Cartões Recompensa do mesmo cliente.

>Observe que os botões usam o parâmetro `[routerLink]`. Esse parâmetro recebe o valor: 

`['/', 'cliente', { outlets: { popup: cliente.id + '/cartao'} }]`

>O parâmetro `[routerLink]` faz o roteamento para a classe `ClienteResolve`, arquivo  `cliente.route.ts` e nessa classe identifica um caminho (`path`) igual a `/cliente/id:/cartao` e, nesse caminho encontra:

```typescript 
  {
    path: ':id/cartao',
    component: ClienteCartaoComponent, //1
    resolve: {
      pagingParams: JhiResolvePagingParams,
      cliente: ClienteResolve //2
    },
    data: { //3
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bestMealApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }

```
>No caminho `/cliente/id:/cartao` são informados:

- O componente que será carregado
- O resolver que instancia uma  instância de `cliente` que será passada para  o componente      `ClienteCartaoComponent`.
- Outros parâmetros que podem ser passados para o componente a ser carregado.  


6. Altere a constante `clientePopupRoute` , conforme a Listagem 4  


```typescript 

export const clientePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClienteDeletePopupComponent,
    resolve: {
      cliente: ClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
 //Início da inclusão  
  , {
    path: ':id/cartao',
    component: ClienteCartaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
      cliente: ClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bestMealApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
  ,
  {
    path: ':id/cartaoRecompensa',
    component: ClienteCartaoRecompensaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
      cliente: ClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bestMealApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: ':id/delete/cartao',
    component: ClienteCartaoCreditoDeletePopupComponent,
    resolve: {
      cartaoCredito: CartaoCreditoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoCredito.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: ':id/delete/cartaoRecompensa',
    component: ClienteCartaoRecompensaDeletePopupComponent,
    resolve: {
      cartaoRecompensa: CartaoRecompensaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bestMealApp.cartaoRecompensa.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
 //Final da inclusão
 ];
```

<p align="center">
   <strong>Listagem 4 - cliente.route.ts </strong> 
</p>

7. Crie 8 novas classes na pasta `cliente`, conforme Listagens 5 a 12

```html
<form name="deleteForm" (ngSubmit)="confirmDelete(cartaoCredito.id)">
    <div class="modal-header">
        <h4 class="modal-title" jhiTranslate="entity.delete.title">Confirm delete operation</h4>
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                (click)="clear()">&times;</button>
    </div>
    <div class="modal-body">
        <jhi-alert-error></jhi-alert-error>
        <p id="jhi-delete-cartaoCredito-heading" jhiTranslate="bestMealApp.cartaoCredito.delete.question" [translateValues]="{id: cartaoCredito.id}">Are you sure you want to delete this Cartao Credito?</p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="clear()">
            <fa-icon [icon]="'ban'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.cancel">Cancel</span>
        </button>
        <button id="jhi-confirm-delete-cartaoCredito" type="submit" class="btn btn-danger">
            <fa-icon [icon]="'times'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.delete">Delete</span>
        </button>
    </div>
</form>

```
<p align="center">
   <strong>Listagem 5 - cliente-cartao-credito-delete-dialog.component.html </strong> 
</p>

::: :pushpin: Importante :::
> Esta view é uma janela de popup que pede confirmação para excluir um cartão de crédito do cliente


```Typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';
import { CartaoCreditoService } from '../cartao-credito/cartao-credito.service';

@Component({
  selector: 'jhi-cliente-cartao-credito-delete-dialog',
  templateUrl: './cliente-cartao-credito-delete-dialog.component.html'
})
export class ClienteCartaoCreditoDeleteDialogComponent {
  cartaoCredito: ICartaoCredito;

  constructor(
    protected cartaoCreditoService: CartaoCreditoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() { // (1)
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) { // (2)
    this.cartaoCreditoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cartaoCreditoListModification',
        content: 'Deleted an cartaoCredito'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cartao-credito-delete-popup',
  template: ''
})
export class ClienteCartaoCreditoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() { // (3)
    this.activatedRoute.data.subscribe(({ cartaoCredito }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteCartaoCreditoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cartaoCredito = cartaoCredito;
        this.ngbModalRef.result.then(
          result => {

           this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoCredito.cliente.id + '/cartao' } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoCredito.cliente.id + '/cartao' } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() { //(4)
    this.ngbModalRef = null;
  }
}

```
<p align="center">
   <strong>Listagem 6 - cliente-cartao-credito-delete-dialog.component.ts </strong> 
</p>

::: :pushpin: Importante :::

>Primeiro é importante destacar que esse arquivo abriga dois componentes: `ClienteCartaoCreditoDeleteDialogComponent` e `ClienteCartaoCreditoDeletePopupComponent`  

>Esta classe é muito rica em informações e vamos destrinchá-las uma a uma.

- Em (1) o método `clear()` usa o método `dismiss` para fechar a janela popup

``` Typescript
  clear() { // (1)
    this.activeModal.dismiss('cancel');
  }
```
- Em (2) o método `confirmDelete` faz um `broadcast` com o nome `cartaoCreditoListModification`. O broadcast será interceptado pelo método `registerChangeInCartaoCreditos` que está na classe `cliente-cartao.component.ts`

```typescript
registerChangeInCartaoCreditos() {
    this.eventSubscriber = this.eventManager.subscribe('cartaoCreditoListModification', response => this.loadAll());
  }

```


```Typescript
  confirmDelete(id: number) { // (2)
    this.cartaoCreditoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cartaoCreditoListModification',
        content: 'Deleted an cartaoCredito'
      });
      this.activeModal.dismiss(true);
    });
  }

```

- Em (3) o método `ngOnInit()` faz duas coisas importantes:

> Usa o método `modalService.open` para abrir a janela popup

> Ao fechar a janela popup (`result` e `reason`) usam o método `router.navigate` para rotear para a view `\cliente\id:\cartao`, ou seja, reexibir a página que estava sendo exibida antes de excluir o cartão. 

```typescript
  ngOnInit() { // (3)
    this.activatedRoute.data.subscribe(({ cartaoCredito }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteCartaoCreditoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cartaoCredito = cartaoCredito;
        this.ngbModalRef.result.then(
          result => {

           this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoCredito.cliente.id + '/cartao' } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoCredito.cliente.id + '/cartao' } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

```
- Em (4), o método `ngOnDestroy()` que faz parte do ciclo de vida do componente, destoi a janela de popup.

```Typescript
  ngOnDestroy() { //(4)
    this.ngbModalRef = null;
  }

```

```html
<form name="deleteForm" (ngSubmit)="confirmDelete(cartaoRecompensa.id)">
    <div class="modal-header">
        <h4 class="modal-title" jhiTranslate="entity.delete.title">Confirm delete operation</h4>
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                (click)="clear()">&times;</button>
    </div>
    <div class="modal-body">
        <jhi-alert-error></jhi-alert-error>
        <p id="jhi-delete-cartaoRecompensa-heading" jhiTranslate="bestMealApp.cartaoRecompensa.delete.question" [translateValues]="{id: cartaoRecompensa.id}">Are you sure you want to delete this Cartao Recompensa?</p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="clear()">
            <fa-icon [icon]="'ban'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.cancel">Cancel</span>
        </button>
        <button id="jhi-confirm-delete-cartaoRecompensa" type="submit" class="btn btn-danger">
            <fa-icon [icon]="'times'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.delete">Delete</span>
        </button>
    </div>
</form>

```
<p align="center">
   <strong>Listagem 7 - cliente-cartao-recompensa-delete-dialog.component.html </strong> 
</p>

::: :pushpin: Importante :::
> Esta view é uma janela de popup que pede confirmação para excluir um cartão de recompensa do cliente


```Typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';
import { CartaoRecompensaService } from '../cartao-recompensa/cartao-recompensa.service';

@Component({
  selector: 'jhi-cliente-cartao-recompensa-delete-dialog',
  templateUrl: './cliente-cartao-recompensa-delete-dialog.component.html'
})
export class ClienteCartaoRecompensaDeleteDialogComponent {
  cartaoRecompensa: ICartaoRecompensa;

  constructor(
    protected cartaoRecompensaService: CartaoRecompensaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cartaoRecompensaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cartaoRecompensaListModification',
        content: 'Deleted an cartaoRecompensa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cartao-recompensa-delete-popup',
  template: ''
})
export class ClienteCartaoRecompensaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cartaoRecompensa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteCartaoRecompensaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cartaoRecompensa = cartaoRecompensa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoRecompensa.cliente.id + '/cartaoRecompensa' } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/', 'cliente', { outlets: { popup: cartaoRecompensa.cliente.id + '/cartaoRecompensa' } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

```

<p align="center">
   <strong>Listagem 8 - cliente-cartao-recompensa-delete-dialog.component.ts </strong> 
</p>

::: :pushpin: Importante :::
> A classe da Listagem 8 é muito semelhante à da Listagem 6 , por isso não serão feitos maiores comentários

```html
<div class="row justify-content-center">
    <div class="col-md-6">
        <h2 id="page-heading">
            <span jhiTranslate="bestMealApp.cartaoRecompensa.home.title">Cartao Recompensas</span>

            <button id="jh-create-entity" class="btn btn-info float-right jh-create-entity create-cartao-recompensa"
                [routerLink]="['/cartao-recompensa/new']" ngbTooltip="{{ 'bestMealApp.cartaoRecompensa.home.createLabel' | translate }}">
                <fa-icon [icon]="'plus'"></fa-icon>
            </button>
        </h2>
        <jhi-alert></jhi-alert>
        <br />
        <div class="table-responsive" *ngIf="cartaoRecompensas">
            <table class="table table-striped">
                <thead>
                    <tr jhiSort [(predicate)]="predicate" [(ascending)]="reverse" [callback]="transition.bind(this)">
                        <th jhiSortBy="id"><span jhiTranslate="global.field.id">ID</span> <fa-icon [icon]="'sort'"></fa-icon></th>
                        <th jhiSortBy="nomeCartao"><span jhiTranslate="bestMealApp.cartaoRecompensa.nomeCartao">Nome Cartao</span> <fa-icon [icon]="'sort'"></fa-icon></th>
                        <th jhiSortBy="numero"><span jhiTranslate="bestMealApp.cartaoRecompensa.numero">Numero</span> <fa-icon [icon]="'sort'"></fa-icon></th>
                        <th jhiSortBy="validade"><span jhiTranslate="bestMealApp.cartaoRecompensa.validade">Validade</span> <fa-icon [icon]="'sort'"></fa-icon></th>
                        <th jhiSortBy="pontuacao"><span jhiTranslate="bestMealApp.cartaoRecompensa.pontuacao">Pontuacao</span> <fa-icon [icon]="'sort'"></fa-icon></th>
                        <th jhiSortBy="situacao"><span jhiTranslate="bestMealApp.cartaoRecompensa.situacao">Situacao</span> <fa-icon [icon]="'sort'"></fa-icon></th>
                        <th></th>
                        </tr>
                            </thead>
                <tbody>
                    <tr *ngFor="let cartaoRecompensa of cartaoRecompensas ;trackBy: trackId">
                        <td>{{cartaoRecompensa.nomeCartao}}</td>
                        <td>{{cartaoRecompensa.numero}}</td>
                        <td>{{cartaoRecompensa.validade}}</td>
                        <td>{{cartaoRecompensa.pontuacao}}</td>
                        <td jhiTranslate="{{'bestMealApp.SituacaoCartao.' + cartaoRecompensa.situacao}}">{{cartaoRecompensa.situacao}}</td>
                        <td>
        
                        <td class="text-right">
                            <div class="btn-group">
                                <button type="submit" [routerLink]="['/cartao-recompensa', cartaoRecompensa.id, 'edit']"
                                    class="btn btn-primary btn-sm" ngbTooltip="{{ 'entity.action.edit' | translate }}">
                                    <fa-icon [icon]="'pencil-alt'"></fa-icon>
                                </button>
                                <button type="submit"
                                    [routerLink]="['/', 'cliente', { outlets: { popup: cartaoRecompensa.id + '/delete/cartaoRecompensa'} }]"
                                    replaceUrl="true" 
                                    queryParamsHandling="merge" 
                                    class="btn btn-danger btn-sm"
                                    ngbTooltip="{{ 'entity.action.delete' | translate }}">
                                    <fa-icon [icon]="'times'"></fa-icon>
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div *ngIf="cartaoRecompensas && cartaoRecompensas.length">
            <div class="row justify-content-center">
                <jhi-item-count [page]="page" [total]="totalItems" [maxSize]="5" [itemsPerPage]="itemsPerPage">
                </jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <ngb-pagination [collectionSize]="totalItems" [(page)]="page" [pageSize]="itemsPerPage" [maxSize]="5"
                    [rotate]="true" [boundaryLinks]="true" (pageChange)="loadPage(page)"></ngb-pagination>
            </div>
        </div>
    </div>
</div>
```
<p align="center">
   <strong>Listagem 9 - cliente-cartao-recompensa.component.html </strong> 
</p>

::: :pushpin: Importante :::
>Esta view mostra a lista de cartoes recompensa e exibe para cada um dos cartões dois botões: um para editar e outro para excluir. 

>Observe que no botão excluir é usado um `routerLink` para `"['/', 'cliente', { outlets: { popup: cartaoRecompensa.id + '/delete/cartaoRecompensa'} }]"`. Este link roteia para a classe `clienteRoute` que está no arquivo `cliente.route.ts`, mais especificamente para o ` path: ':id/delete/cartaoRecompensa'`. 



```Typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CartaoCreditoService } from '../cartao-credito';
import { ClienteService } from './cliente.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ICartaoRecompensa } from 'app/shared/model/cartao-recompensa.model';

@Component({
  selector: 'jhi-cliente-cartao-recompensa',
  templateUrl: './cliente-cartao-recompensa.component.html'
})
export class ClienteCartaoRecompensaComponent implements OnInit, OnDestroy {
  cliente: ICliente;

  currentAccount: any;
  cartaoRecompensas: ICartaoRecompensa[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected cartaoCreditoService: CartaoCreditoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected clienteService: ClienteService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.clienteService
      .queryAllCartaoRecompensa({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        id: this.cliente.id
      })
      .subscribe(
        (res: HttpResponse<ICartaoRecompensa[]>) => this.paginateCartaoRecompensas(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/cliente', this.cliente.id, 'cartaoRecompensa'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/cartao-recompensa',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    /*
    subscreve para o resolve a fim de receber o cliente.
    vide cliente-cartao.componente.html e cliente.route.ts
    */
    this.activatedRoute.data.subscribe(({ cliente }) => { //1
      this.cliente = cliente;
    });

    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCartaoCreditos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICartaoCredito) {
    return item.id;
  }

  registerChangeInCartaoCreditos() { //2
    this.eventSubscriber = this.eventManager.subscribe('cartaoRecompensaListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCartaoRecompensas(data: ICartaoRecompensa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.cartaoRecompensas = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

```
<p align="center">
   <strong>Listagem 10 - cliente-cartao-recompensa.component.ts </strong> 
</p>

::: :pushpin: Importante :::
>(1)-Subscreve-se para receber uma instância de Cliente do resolver

>(2)-Registra-se para receber o broadCast `cartaoRecompensaListModification` 

```Typescript
<div class="row justify-content-center">
    <div class="col-md-6">
        <h2 id="page-heading">
            <span jhiTranslate="bestMealApp.cartaoCredito.home.title">Cartao Creditos</span>

            <button id="jh-create-entity" class="btn btn-info float-right jh-create-entity create-cartao-credito"
                [routerLink]="['/cartao-credito/new']" ngbTooltip="{{ 'bestMealApp.cartaoCredito.home.createLabel' | translate }}">
                <fa-icon [icon]="'plus'"></fa-icon>
            </button>
        </h2>
        <jhi-alert></jhi-alert>
        <br />
        <div class="table-responsive" *ngIf="cartaoCreditos">
            <table class="table table-striped">
                <thead>
                    <tr jhiSort [(predicate)]="predicate" [(ascending)]="reverse" [callback]="transition.bind(this)">
                        <th jhiSortBy="nomeCartao"><span jhiTranslate="bestMealApp.cartaoCredito.nomeCartao">Nome
                                Cartao</span>
                            <fa-icon [icon]="'sort'"></fa-icon>
                        </th>
                        <th jhiSortBy="bandeira"><span jhiTranslate="bestMealApp.cartaoCredito.bandeira">Bandeira</span>
                            <fa-icon [icon]="'sort'"></fa-icon>
                        </th>
                        <th jhiSortBy="numero"><span jhiTranslate="bestMealApp.cartaoCredito.numero">Numero</span>
                            <fa-icon [icon]="'sort'"></fa-icon>
                        </th>
                        <th jhiSortBy="cvv"><span jhiTranslate="bestMealApp.cartaoCredito.cvv">Cvv</span>
                            <fa-icon [icon]="'sort'"></fa-icon>
                        </th>
                        <th jhiSortBy="validade"><span jhiTranslate="bestMealApp.cartaoCredito.validade">Validade</span>
                            <fa-icon [icon]="'sort'"></fa-icon>
                        </th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr *ngFor="let cartaoCredito of cartaoCreditos ;trackBy: trackId">
                        <td>{{cartaoCredito.nomeCartao}}</td>
                        <td jhiTranslate="{{'bestMealApp.Bandeira.' + cartaoCredito.bandeira}}">
                            {{cartaoCredito.bandeira}}</td>
                        <td>{{cartaoCredito.numero}}</td>
                        <td>{{cartaoCredito.cvv}}</td>
                        <td>{{cartaoCredito.validade}}</td>

                        <td class="text-right">
                            <div class="btn-group">
                                <button type="submit" [routerLink]="['/cartao-credito', cartaoCredito.id, 'edit']"
                                    class="btn btn-primary btn-sm" ngbTooltip="{{ 'entity.action.edit' | translate }}">
                                    <fa-icon [icon]="'pencil-alt'"></fa-icon>
                                </button>
                                <button type="submit"
                                    [routerLink]="['/', 'cliente', { outlets: { popup: cartaoCredito.id + '/delete/cartao'} }]"
                                    replaceUrl="true" queryParamsHandling="merge" class="btn btn-danger btn-sm"
                                    ngbTooltip="{{ 'entity.action.delete' | translate }}">
                                    <fa-icon [icon]="'times'"></fa-icon>
                                </button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div *ngIf="cartaoCreditos && cartaoCreditos.length">
            <div class="row justify-content-center">
                <jhi-item-count [page]="page" [total]="totalItems" [maxSize]="5" [itemsPerPage]="itemsPerPage">
                </jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <ngb-pagination [collectionSize]="totalItems" [(page)]="page" [pageSize]="itemsPerPage" [maxSize]="5"
                    [rotate]="true" [boundaryLinks]="true" (pageChange)="loadPage(page)"></ngb-pagination>
            </div>
        </div>
    </div>
</div>
```
<p align="center">
   <strong>Listagem 11 - cliente-cartao.component.html </strong> 
</p>

::: :pushpin: Importante :::
>Obs idêntida à Listagem 9

```Typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICartaoCredito } from 'app/shared/model/cartao-credito.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CartaoCreditoService } from '../cartao-credito';
import { ClienteService } from './cliente.service';
import { ICliente } from 'app/shared/model/cliente.model';

@Component({
  selector: 'jhi-cliente-cartao',
  templateUrl: './cliente-cartao.component.html'
})
export class ClienteCartaoComponent implements OnInit, OnDestroy {
  cliente: ICliente;

  currentAccount: any;
  cartaoCreditos: ICartaoCredito[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected cartaoCreditoService: CartaoCreditoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected clienteService: ClienteService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.clienteService
      .queryAllCartaoCredito({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        id: this.cliente.id
      })
      .subscribe(
        (res: HttpResponse<ICartaoCredito[]>) => this.paginateCartaoCreditos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/cartao-credito'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/cartao-credito',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    /*
    subscreve para o resolve a fim de receber o cliente.
    vide cliente-cartao.componente.html e cliente.route.ts
    */
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.cliente = cliente;
    });

    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCartaoCreditos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICartaoCredito) {
    return item.id;
  }

  registerChangeInCartaoCreditos() {
    this.eventSubscriber = this.eventManager.subscribe('cartaoCreditoListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCartaoCreditos(data: ICartaoCredito[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.cartaoCreditos = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

```
<p align="center">
   <strong>Listagem 12 - cliente-cartao.component.ts </strong> 
</p>

::: :pushpin: Importante :::

>Obs - Identica à Listagem 10

8. No arquivo `cliente.service.ts` adicione:

```typeScript

queryAllCartaoCredito(req?: any): Observable<EntityArrayResponseType> { //(1)
    const options = createRequestOption(req);
    return this.http.get<ICartaoCredito[]>(`${this.resourceUrl}/cartao-creditos`, { params: options, observe: 'response' });
  }

  queryAllCartaoRecompensa(req?: any): Observable<EntityArrayResponseType> { //(2)
    const options = createRequestOption(req);
    return this.http.get<ICartaoRecompensa[]>(`${this.resourceUrl}/cartao-recompensa`, { params: options, observe: 'response' });
  }

  setCliente(cliente: ICliente) { //(3)
    this.cliente = cliente;
  }
  getCliente(): ICliente {
    return this.cliente;
  }
```

::: :pushpin: Importante :::

>(1) - O método `queryAllCartaoCredito()` aciona o Rest em `/api/clientes/cartao-creditos`

>(2) - O método `queryAllCartaoCredito()` aciona o Rest em `/api/clientes/cartao-recompensa`

>(3) - Método `get/set` para atribuir uma instância de Cliente à variável cliente

9. Adicione as seguintes linhas ao arquivo `src/main/webapp/app/entities/cliente/index.ts`


```html
export * from './cliente-cartao.component';
export * from './cliente-cartao-recompensa.component';
export * from './cliente-cartao-credito-delete-dialog.component';
export * from './cliente-cartao-recompensa-delete-dialog.component';

```

10. Altere o arquivo `cliente.module.ts` para que fique idêntico à Listagem 13

```typescript
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BestMealSharedModule } from 'app/shared';
import {
  ClienteComponent,
  ClienteDetailComponent,
  ClienteUpdateComponent,
  ClienteDeletePopupComponent,
  ClienteDeleteDialogComponent,
  clienteRoute,
  clientePopupRoute,
  ClienteCartaoComponent,
  ClienteCartaoRecompensaComponent,
  ClienteCartaoCreditoDeleteDialogComponent,
  ClienteCartaoCreditoDeletePopupComponent,
  ClienteCartaoRecompensaDeleteDialogComponent,
  ClienteCartaoRecompensaDeletePopupComponent
} from './';

const ENTITY_STATES = [...clienteRoute, ...clientePopupRoute];

@NgModule({
  imports: [BestMealSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClienteComponent,
    ClienteDetailComponent,
    ClienteUpdateComponent,
    ClienteDeleteDialogComponent,
    ClienteDeletePopupComponent,
    ClienteCartaoComponent,
    ClienteCartaoRecompensaComponent,
    ClienteCartaoCreditoDeleteDialogComponent,
    ClienteCartaoCreditoDeletePopupComponent,
    ClienteCartaoRecompensaDeleteDialogComponent,
    ClienteCartaoRecompensaDeletePopupComponent

  ],
  entryComponents: [
    ClienteComponent,
    ClienteUpdateComponent,
    ClienteDeleteDialogComponent,
    ClienteDeletePopupComponent,
    ClienteCartaoComponent,
    ClienteCartaoRecompensaComponent,
    ClienteCartaoCreditoDeleteDialogComponent,
    ClienteCartaoCreditoDeletePopupComponent,
    ClienteCartaoRecompensaDeleteDialogComponent,
    ClienteCartaoRecompensaDeletePopupComponent

  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BestMealClienteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}

```
<p align="center">
   <strong>Listagem 13 - cliente-module.ts </strong> 
</p>

::: :pushpin: Importante :::

>Essa classe registra os componentes criados para que sejam visíveis por outros módulos


#### Implementações do lado servidor

::: :walking: Passo a passo :::

1. No lado servidor serão alteradas 7 classes. A primeira delas é  `ClienteResource.java`. Faça a inserção do trecho de código da Listagem 14.


```java

@Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClienteService clienteService;
    private final CartaoCreditoService cartaoCreditoService; //inserida
    private final CartaoRecompensaService cartaoRecompensaService; //inserida

public ClienteResource(ClienteService clienteService, //alterada
    CartaoCreditoService cartaoCreditoService,
    CartaoRecompensaService cartaoRecompensaService)
     {
        this.clienteService = clienteService;
        this.cartaoCreditoService = cartaoCreditoService;//inserida
        this.cartaoRecompensaService  = cartaoRecompensaService; //inserida
    }

// .... outras linhas sem alteração aqui

//Inserir
    @GetMapping("/clientes/cartao-creditos")
    public ResponseEntity<List<CartaoCredito>> getAllCartaoCreditos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        Long id = Long.parseLong(queryParams.get("id").get(0));
        log.debug("REST request to get all Credit Cards of Cliente {} ", id);
        Page<CartaoCredito> page = cartaoCreditoService.findAllClienteCartaoCredito(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/clientes/cartao-recompensa")
    public ResponseEntity<List<CartaoRecompensa>> getAllCartaoRecompensa(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        Long id = Long.parseLong(queryParams.get("id").get(0));
        log.debug("REST request to get all Revenew Cards of Cliente {} ", id);
        Page<CartaoRecompensa> page = cartaoRecompensaService.findAllClienteCartaoRecompensa(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
//Final da inserção

```
<p align="center">
   <strong>Listagem 14 - ClienteResource.java </strong> 
</p>

::: :pushpin: Importante :::

>Nessa Classe é importante notar os dois métodos `Http.Get` criados (um para cartão de crédito e o outro para cartão recompensa). Esses dois métodos são a porta de entrada e saida do lado servidor. Comunicam-se com `cliente.service.ts` no lado cliente.

2. Altere as classes `CartaoRecompensaService.java`, adicionando uma linha, conforme Listagem 15.

```java
	Page<CartaoRecompensa> findAllClienteCartaoRecompensa(Long id, Pageable pageable);
```
<p align="center">
   <strong>Listagem 15 - CartaoRecompensaService.java </strong> 
</p>

3. Altere as classes `CartaoRecompensaServiceImpl.java`, adicionando as linhas, conforme Listagem 16.

```java
    @Override
    public Page<CartaoRecompensa> findAllClienteCartaoRecompensa(Long id, Pageable pageable) {
        log.debug("Request to get all  CartaoRecompensa of Cliente: {}", id);
        return cartaoRecompensaRepository.findAllClienteCartaoRecompensa(id, pageable);
    }
```
<p align="center">
   <strong>Listagem 16 - CartaoRecompensaServiceImpl.java </strong> 
</p>

4. Altere as classes `CartaoRecompensaRepository.java`, adicionando as linhas, conforme Listagem 17.

```java
    @Query("SELECT c FROM CartaoRecompensa c WHERE c.cliente.id = :id ")
	Page<CartaoRecompensa> findAllClienteCartaoRecompensa(@Param("id") Long id, Pageable pageable);

```
<p align="center">
   <strong>Listagem 17 - CartaoRecompensaRepository.java </strong> 
</p>

::: :pushpin: Importante :::

>O que esse método faz é usar a JPQL (OQL para a JPA) para selecionar os cartões recompensa de um cliente cujo `id` é fornecido como parâmetro.

5. Faça você mesmo as alterações em `CartaoCreditoService.java`, `CartaoCreditoServiceImpl.java` e `CartaoCreditoRepository.java`.

#### Melhorando a aparência da aplicação

::: :walking: Passo a passo :::

1. Altere o arquivo `vendor.ts`

```typescript
  faCreditCard, //adicionada
  faBookmark,   //adicionada
  faMoneyCheck  //adicionada
  
} from '@fortawesome/free-solid-svg-icons';

/// .... sem alteração

library.add(faCheck);
library.add(faCreditCard);//adicionada
library.add(faBookmark); //adicionada
library.add(faMoneyCheck) //adicionada


// jhipster-needle-add-element-to-vendor - JHipster will add new menu items her

```

::: :pushpin: Importante :::

>A classes `vendor.ts`é utilizada pata incluir fontes e ícones da bilbioteca `fontawesome` 

>Observe que o ícone `faCreditCard` é usado assim:

```html
<fa-icon [icon]="'credit-card'"></fa-icon>
```

2. Altere o arquivo `src/main/webapp/i18n/pt-br/global.json`

```json
"entity": {
    "action": {
      "addblob": "Adicionar arquivo",
      "addimage": "Adicionar imagem",
      "back": "Voltar",
      "cancel": "Cancelar",
      "delete": "Excluir",
      "edit": "Editar",
      "open": "Abrir",
      "save": "Salvar",
      "view": "Visualizar",
      "managecredit": "C. Crédito", //adicionado
      "managereward": "C. Recompensa" //adicionado

    },
```

2. Altere o arquivo `src/main/webapp/i18n/pt-br/pessoa.json`

```json
  "endereco": "Dados do Endereço:",
      "dadosGerais": "Dados Gerais:",
      "nomeFantasia": "Nome de Fantasia",
      "gerenciarCartoes": "Gerenciamento de Cartões" //adicionado
    }
  }
}

```

#### Pronto!
