# BestMeal - Melhorias na Entidade Pessoa

## O que será feito?

> Após a criação da entidade pessoas, verificamos que diversas melhorias podem ser incorporadas:
1. Selecionar alguns campos para exibir no `pessoa.component.html`
2. Exibir nome do município e a UF na combo
3. Validação de CPF e Validação de CNPJ
4. Lay out do formulário


::: :walking: Passo a passo :::

#### 1. Selecionar alguns campos para exibir no `pessoa.component.html`

> Se você executar a aplicação verá que existem muitos campos sendo mostrados no componente `pessoa.component.html`. Isso prejudica a aparência e a legibilidade da aplicação. Vamos então selecionar alguns campos para exibir.

::: :walking: Passo a passo :::

1. Exclua algumas colunas desnecessárias do  arquivo `pessoa.component.html` conforme Listagem 1  

```html
<div>
    <h2 id="page-heading">
        <span jhiTranslate="bestMealApp.pessoa.home.title">Pessoas</span>
        <button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-pessoa" [routerLink]="['/pessoa/new']">
            <fa-icon [icon]="'plus'"></fa-icon>
            <span  jhiTranslate="bestMealApp.pessoa.home.createLabel">
            Create new Pessoa
            </span>
        </button>
    </h2>
    <jhi-alert></jhi-alert>
    <br/>
    <div class="table-responsive" *ngIf="pessoas">
        <table class="table table-striped">
            <thead>
            <tr jhiSort [(predicate)]="predicate" [(ascending)]="reverse" [callback]="transition.bind(this)">
            <th jhiSortBy="id"><span jhiTranslate="global.field.id">ID</span> <fa-icon [icon]="'sort'"></fa-icon></th>
            <th jhiSortBy="tipo"><span jhiTranslate="bestMealApp.pessoa.tipo">Tipo</span> <fa-icon [icon]="'sort'"></fa-icon></th>
            <th jhiSortBy="titulo"><span jhiTranslate="bestMealApp.pessoa.titulo">Titulo</span> <fa-icon [icon]="'sort'"></fa-icon></th>
            <th jhiSortBy="primeiroNome"><span jhiTranslate="bestMealApp.pessoa.primeiroNome">Primeiro Nome</span> <fa-icon [icon]="'sort'"></fa-icon></th>
            <th jhiSortBy="municipio.nomeMunicipio"><span jhiTranslate="bestMealApp.pessoa.municipio">Municipio</span> <fa-icon [icon]="'sort'"></fa-icon></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr *ngFor="let pessoa of pessoas ;trackBy: trackId">
                <td><a [routerLink]="['/pessoa', pessoa.id, 'view' ]">{{pessoa.id}}</a></td>
                <td jhiTranslate="{{'bestMealApp.TipoPessoa.' + pessoa.tipo}}">{{pessoa.tipo}}</td>
                <td>{{pessoa.titulo}}</td>
                <td>{{pessoa.primeiroNome}}</td>
                <td>
                    <div *ngIf="pessoa.municipio">
                        <a [routerLink]="['../municipio', pessoa.municipio?.id, 'view' ]" >{{pessoa.municipio?.nomeMunicipio}}</a>
                    </div>
                </td>
                <td class="text-right">
                    <div class="btn-group">
                        <button type="submit"
                                [routerLink]="['/pessoa', pessoa.id, 'view' ]"
                                class="btn btn-info btn-sm">
                            <fa-icon [icon]="'eye'"></fa-icon>
                            <span class="d-none d-md-inline" jhiTranslate="entity.action.view">View</span>
                        </button>
                        <button type="submit"
                                [routerLink]="['/pessoa', pessoa.id, 'edit']"
                                class="btn btn-primary btn-sm">
                            <fa-icon [icon]="'pencil-alt'"></fa-icon>
                            <span class="d-none d-md-inline" jhiTranslate="entity.action.edit">Edit</span>
                        </button>
                        <button type="submit"
                                [routerLink]="['/', 'pessoa', { outlets: { popup: pessoa.id + '/delete'} }]"
                                replaceUrl="true"
                                queryParamsHandling="merge"
                                class="btn btn-danger btn-sm">
                            <fa-icon [icon]="'times'"></fa-icon>
                            <span class="d-none d-md-inline" jhiTranslate="entity.action.delete">Delete</span>
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div *ngIf="pessoas && pessoas.length">
        <div class="row justify-content-center">
            <jhi-item-count [page]="page" [total]="totalItems" [maxSize]="5" [itemsPerPage]="itemsPerPage"></jhi-item-count>
        </div>
        <div class="row justify-content-center">
            <ngb-pagination [collectionSize]="totalItems" [(page)]="page" [pageSize]="itemsPerPage" [maxSize]="5" [rotate]="true" [boundaryLinks]="true" (pageChange)="loadPage(page)"></ngb-pagination>
        </div>
    </div>
</div>

```


<p align="center">
   <strong>Listagem 1 - pessoa.component.html </strong> 
</p>

2. No mesmo arquivo altere o nome que será exibido.

- substitua:

```typescript
            <th jhiSortBy="primeiroNome"><span jhiTranslate="bestMealApp.pessoa.primeiroNome">Primeiro Nome</span> <fa-icon [icon]="'sort'"></fa-icon></th>
... 
            <td>{{pessoa.primeiroNome}}</td>
```

- por:

```typescript
            <th jhiSortBy="sobreNome"><span jhiTranslate="bestMealApp.pessoa.nomePessoa">Nome da Pessoa</span> <fa-icon [icon]="'sort'"></fa-icon></th>
...
            <td>{{pessoa.sobreNome}}, {{pessoa.primeiroNome}}</td>
```

3. Acrescente uma chave nova no arquivo `src/main/webapp/i18n/pt-br/pessoa.json`.

```json
      "complemento": "Complemento",
      "municipio": "Municipio",      // >> acrescentar a vírgula e
      "nomePessoa": "Nome da Pessoa" // >> acrescentar esta linha aqui
```


#### 2. Exibir nome do município e a UF na combo

> Se você verificar a combo que exibe a relação de municípios, constatará que a UF não é exibida. Vamos melhorar a aplicação para exibir na combo o nome do município e a UF.

1. No arquivo   `src/main/webapp/app/entities/pessoa/pessoa-update.component.html` 

- Altere:

```html
              <div class="form-group">
                    <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.municipio" for="field_municipio">Municipio</label>
                    <select class="form-control" id="field_municipio" name="municipio" formControlName="municipio">
                        <option [ngValue]="null"></option>
                        <option [ngValue]="municipioOption.id === editForm.get('municipio').value?.id ? editForm.get('municipio').value : municipioOption" *ngFor="let municipioOption of municipios; trackBy: trackMunicipioById">{{municipioOption.nomeMunicipio}}</option>
                    </select>
                </div>
            </div>

```

- Por:

```html
                    <div class="form-group col-md-6">
                        <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.municipio"
                            for="field_municipio">Municipio</label>
                        <select class="form-control" id="field_municipio" name="municipio" formControlName="municipio">
                            <option [ngValue]="null"></option>
                            <option
                                [ngValue]="municipioOption.id === editForm.get('municipio').value?.id ? editForm.get('municipio').value : municipioOption"
                                *ngFor="let municipioOption of municipios; trackBy: trackMunicipioById">
                                {{municipioOption.nomeMunicipio+"-"+municipioOption.uf}}</option>
                        </select>
                    </div>


```

2. No arquivo   `src/main/webapp/app/entities/pessoa/pessoa.component.html` 

- Altere:

```html
                    <div *ngIf="pessoa.municipio">
                        <a [routerLink]="['../municipio', pessoa.municipio?.id, 'view' ]" >{{pessoa.municipio?.nomeMunicipio}}</a>
                    </div>

```

- Por:

```html
                    <div *ngIf="pessoa.municipio">
                        <a [routerLink]="['../municipio', pessoa.municipio?.id, 'view' ]" >{{pessoa.municipio?.nomeMunicipio+"-"+pessoa.municipio?.uf}}</a>
                    </div>

```
#### 3. Validação de CPF e CNPJ

1. Na pasta `src/main/webapp/app/shared/validators`, crie um arquivo denominado `cpf-cnpj-validators.service.ts`  para fazer as validações dos dígitos verificadores dp CPF e CNPJ. 

```typescript
import { AbstractControl, ValidatorFn } from '@angular/forms';
import { Injectable } from '@angular/core';

/**
 * Classe para fazer validações no dv de cpf e cnpj
 *
 */

@Injectable({ providedIn: 'root' })
export class CustomCPFCNPJValidatorService {
    /**
     * Método pafa validar o cpf.
     * O cpf deve estar com máscara.
     * A funcao retira a máscara
     */
    isValidCpf(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } => {
            if (control.value) {
                const cpf = control.value.replace(/\D/g, ''); // retirar mascara do cpf
                let numbers, digits, sum, i, result, equalDigits;
                equalDigits = 1;
                if (cpf.length < 11) {
                    return null;
                }

                for (i = 0; i < cpf.length - 1; i++) {
                    if (cpf.charAt(i) !== cpf.charAt(i + 1)) {
                        equalDigits = 0;
                        break;
                    }
                }

                if (!equalDigits) {
                    numbers = cpf.substring(0, 9);
                    digits = cpf.substring(9);
                    sum = 0;
                    for (i = 10; i > 1; i--) {
                        sum += numbers.charAt(10 - i) * i;
                    }

                    result = sum % 11 < 2 ? 0 : 11 - (sum % 11);

                    if (result !== Number(digits.charAt(0))) {
                        return { cpfNotValid: { value: control.value } };
                    }
                    numbers = cpf.substring(0, 10);
                    sum = 0;

                    for (i = 11; i > 1; i--) {
                        sum += numbers.charAt(11 - i) * i;
                    }
                    result = sum % 11 < 2 ? 0 : 11 - (sum % 11);

                    if (result !== Number(digits.charAt(1))) {
                        return { cpfNotValid: { value: control.value } };
                    }
                    return null;
                } else {
                    return { cpfNotValid: { value: control.value } };
                }
            }
            return null;
        };
    }
    /**
     * Método pafa validar o cnpj.
     * O cpf deve estar com máscara.
     * A funcao retira a máscara
     */

    isValidCnpj(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } => {

            if (control.value) {
                const cnpj = control.value.replace(/\D/g, ''); // retirar mascara do cpf
                let tamanho, numeros, digitos, soma, pos, i, resultado;

                if (cnpj.length < 14) {
                    return null;
                }
                // Elimina CNPJs invalidos conhecidos
                if (cnpj === '00000000000000' ||
                    cnpj === '11111111111111' ||
                    cnpj === '22222222222222' ||
                    cnpj === '33333333333333' ||
                    cnpj === '44444444444444' ||
                    cnpj === '55555555555555' ||
                    cnpj === '66666666666666' ||
                    cnpj === '77777777777777' ||
                    cnpj === '88888888888888' ||
                    cnpj === '99999999999999') {
                    return { cnpjNotValid: { value: control.value } };
                }
                // Valida DVs
                tamanho = cnpj.length - 2;
                numeros = cnpj.substring(0, tamanho);
                digitos = cnpj.substring(tamanho);
                soma = 0;
                pos = tamanho - 7;
                for (i = tamanho; i >= 1; i--) {
                    soma += numeros.charAt(tamanho - i) * pos--;
                    if (pos < 2) {
                        pos = 9;
                    }
                }
                resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
                if (resultado !== Number(digitos.charAt(0))) {
                    return { cnpjNotValid: { value: control.value } };
                }
                tamanho = tamanho + 1;
                numeros = cnpj.substring(0, tamanho);
                soma = 0;
                pos = tamanho - 7;
                for (i = tamanho; i >= 1; i--) {
                    soma += numeros.charAt(tamanho - i) * pos--;
                    if (pos < 2) {
                        pos = 9;
                    }
                }
                resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
                if (resultado !== Number(digitos.charAt(1))) {
                    return { cnpjNotValid: { value: control.value } };
                }

                return null;
            }
        };
    }
}
```
<p align="center">
   <strong>Listagem 2 - `cpf-cnpj-validators.service.ts` </strong> 
</p>

2. Faça as alterações abaixo no arquivo `pessoa-update.component.ts`. 

- Altere:

```typescript
    cpf: [],
    cnpj: [],
```

- Por:

```typescript
    cpf: [null, [Validators.pattern('^[0-9]{3}\.?[0-9]{3}\.?[0-9]{3}\-?[0-9]{2}$'),
    this.customCPFCNPJValidatorService.isValidCpf()]],
    cnpj: [null, [this.customCPFCNPJValidatorService.isValidCnpj(),
      Validators.pattern('^[0-9]{2}\.?[0-9]{3}\.?[0-9]{3}\/?[0-9]{4}\-?[0-9]{2}$')
    ]],
```

3. Adicione a linha abaixo no arquivo `pessoa-update.component.ts`. 

```typescript
import { CustomCPFCNPJValidatorService } from 'app/shared/validators/cpf-cnpj-validators.service';
```

4. No arquivo `pessoa-update.component.ts` faça a seguinte alteração. 


- Altere:

```typescript
constructor(
    protected jhiAlertService: JhiAlertService,
    protected pessoaService: PessoaService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

```

- Por:

```typescript
constructor(
    protected jhiAlertService: JhiAlertService,
    protected pessoaService: PessoaService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected customCPFCNPJValidatorService: CustomCPFCNPJValidatorService //>>Linha adicionada
  ) {}

```
#### 4. Lay out do formulário

> Para melhorar a usabilidade do formulário, altere o arquivo  `pessoa-update.component.ts` para que fique conforme a Listagem 3 

```html
<div class="row justify-content-center">
    <div class="col-md-6">
        <form name="editForm" role="form" novalidate (ngSubmit)="save()" [formGroup]="editForm">
            <h2 id="jhi-pessoa-heading" jhiTranslate="bestMealApp.pessoa.home.createOrEditLabel">Create or edit a Pessoa
            </h2>
            <div>
                <jhi-alert-error></jhi-alert-error>

                <div class="row">
                    <div class="form-group col-md-3" [hidden]="!pessoa.id">
                        <label for="id" jhiTranslate="global.field.id">ID</label>
                        <input type="text" class="form-control" id="id" name="id" formControlName="id" readonly />
                    </div>
                </div>
                <div>
                    <small class="form-text text-primary" jhiTranslate="bestMealApp.pessoa.dadosGerais">
                        Dados Gerais:
                    </small>
                    <hr class="half-rule" />
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.tipo"
                            for="field_tipo">Tipo</label>
                        <select class="form-control" name="tipo" formControlName="tipo" id="field_tipo">
                            <option value="FISICA">{{'bestMealApp.TipoPessoa.FISICA' | translate}}</option>
                            <option value="JURIDICA">{{'bestMealApp.TipoPessoa.JURIDICA' | translate}}</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <div *ngIf="(editForm.get(['tipo']).value === 'FISICA')">
                            <div class="form-group">
                                <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.cpf"
                                    for="field_cpf">Cpf</label>
                                <input type="text" class="form-control" name="cpf" id="field_cpf"
                                    formControlName="cpf" />
                                <div
                                    *ngIf="editForm.get('cpf').invalid && (editForm.get('cpf').dirty || editForm.get('cpf').touched)">
                                    <small class="form-text text-danger" *ngIf="editForm.get('cpf').errors.pattern"
                                        jhiTranslate="entity.validation.pattern" [translateValues]="{ pattern: 'CPF' }">
                                        This field should follow pattern for "CPF".
                                    </small>
                                    <small class="form-text text-danger" *ngIf="editForm.get('cpf').errors.cpfNotValid"
                                        jhiTranslate="entity.validation.cpfdigit">
                                        Digito do CPF é inválido.
                                    </small>
                                </div>
                            </div>
                        </div>
                        <div *ngIf="(editForm.get(['tipo']).value === 'JURIDICA')">
                            <div class="form-group">
                                <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.cnpj"
                                    for="field_cnpj">Cnpj</label>
                                <input type="text" class="form-control" name="cnpj" id="field_cnpj"
                                    formControlName="cnpj" />
                                <div
                                    *ngIf="editForm.get('cnpj').invalid && (editForm.get('cnpj').dirty || editForm.get('cnpj').touched)">
                                    <small class="form-text text-danger" *ngIf="editForm.get('cnpj').errors.pattern"
                                        jhiTranslate="entity.validation.pattern"
                                        [translateValues]="{ pattern: 'CNPJ' }">
                                        This field should follow pattern for "CNPJ".
                                    </small>
                                    <small class="form-text text-danger"
                                        *ngIf="editForm.get('cnpj').errors.cnpjNotValid"
                                        jhiTranslate="entity.validation.cnpjdigit">
                                        Digito do CPF é inválido.
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div *ngIf="(editForm.get(['tipo']).value === 'JURIDICA')">

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.nomeFantasia"
                                for="field_primeiroNome">Primeiro Nome</label>
                            <input type="text" class="form-control" name="primeiroNome" id="field_primeiroNome"
                                formControlName="primeiroNome" />
                            <div
                                *ngIf="editForm.get('primeiroNome').invalid && (editForm.get('primeiroNome').dirty || editForm.get('primeiroNome').touched)">
                                <small class="form-text text-danger"
                                    *ngIf="editForm.get('primeiroNome').errors.required"
                                    jhiTranslate="entity.validation.required">
                                    This field is required.
                                </small>
                                <small class="form-text text-danger"
                                    *ngIf="editForm.get('primeiroNome').errors.minlength"
                                    jhiTranslate="entity.validation.minlength" [translateValues]="{ min: 2 }">
                                    This field is required to be at least 2 characters.
                                </small>
                                <small class="form-text text-danger"
                                    *ngIf="editForm.get('primeiroNome').errors.maxlength"
                                    jhiTranslate="entity.validation.maxlength" [translateValues]="{ max: 20 }">
                                    This field cannot be longer than 20 characters.
                                </small>
                            </div>
                        </div>
                    </div>
                </div>
                <div *ngIf="(editForm.get(['tipo']).value === 'FISICA')">

                    <div class="row">
                        <div class="form-group col-md-4">
                            <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.primeiroNome"
                                for="field_primeiroNome">Primeiro Nome</label>
                            <input type="text" class="form-control" name="primeiroNome" id="field_primeiroNome"
                                formControlName="primeiroNome" />
                            <div
                                *ngIf="editForm.get('primeiroNome').invalid && (editForm.get('primeiroNome').dirty || editForm.get('primeiroNome').touched)">
                                <small class="form-text text-danger"
                                    *ngIf="editForm.get('primeiroNome').errors.required"
                                    jhiTranslate="entity.validation.required">
                                    This field is required.
                                </small>
                                <small class="form-text text-danger"
                                    *ngIf="editForm.get('primeiroNome').errors.minlength"
                                    jhiTranslate="entity.validation.minlength" [translateValues]="{ min: 2 }">
                                    This field is required to be at least 2 characters.
                                </small>
                                <small class="form-text text-danger"
                                    *ngIf="editForm.get('primeiroNome').errors.maxlength"
                                    jhiTranslate="entity.validation.maxlength" [translateValues]="{ max: 20 }">
                                    This field cannot be longer than 20 characters.
                                </small>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.nomeMeio"
                                for="field_nomeMeio">Nome Meio</label>
                            <input type="text" class="form-control" name="nomeMeio" id="field_nomeMeio"
                                formControlName="nomeMeio" />
                            <div
                                *ngIf="editForm.get('nomeMeio').invalid && (editForm.get('nomeMeio').dirty || editForm.get('nomeMeio').touched)">
                                <small class="form-text text-danger" *ngIf="editForm.get('nomeMeio').errors.required"
                                    jhiTranslate="entity.validation.required">
                                    This field is required.
                                </small>
                                <small class="form-text text-danger" *ngIf="editForm.get('nomeMeio').errors.minlength"
                                    jhiTranslate="entity.validation.minlength" [translateValues]="{ min: 2 }">
                                    This field is required to be at least 2 characters.
                                </small>
                                <small class="form-text text-danger" *ngIf="editForm.get('nomeMeio').errors.maxlength"
                                    jhiTranslate="entity.validation.maxlength" [translateValues]="{ max: 30 }">
                                    This field cannot be longer than 30 characters.
                                </small>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.sobreNome"
                                for="field_sobreNome">Sobre Nome</label>
                            <input type="text" class="form-control" name="sobreNome" id="field_sobreNome"
                                formControlName="sobreNome" />
                            <div
                                *ngIf="editForm.get('sobreNome').invalid && (editForm.get('sobreNome').dirty || editForm.get('sobreNome').touched)">
                                <small class="form-text text-danger" *ngIf="editForm.get('sobreNome').errors.required"
                                    jhiTranslate="entity.validation.required">
                                    This field is required.
                                </small>
                                <small class="form-text text-danger" *ngIf="editForm.get('sobreNome').errors.minlength"
                                    jhiTranslate="entity.validation.minlength" [translateValues]="{ min: 2 }">
                                    This field is required to be at least 2 characters.
                                </small>
                                <small class="form-text text-danger" *ngIf="editForm.get('sobreNome').errors.maxlength"
                                    jhiTranslate="entity.validation.maxlength" [translateValues]="{ max: 30 }">
                                    This field cannot be longer than 30 characters.
                                </small>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.saudacao"
                                for="field_saudacao">Saudacao</label>
                            <input type="text" class="form-control" name="saudacao" id="field_saudacao"
                                formControlName="saudacao" />
                        </div>
                        <div class="form-group col-md-6">
                            <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.titulo"
                                for="field_titulo">Titulo</label>
                            <input type="text" class="form-control" name="titulo" id="field_titulo"
                                formControlName="titulo" />
                            <div
                                *ngIf="editForm.get('titulo').invalid && (editForm.get('titulo').dirty || editForm.get('titulo').touched)">
                                <small class="form-text text-danger" *ngIf="editForm.get('titulo').errors.required"
                                    jhiTranslate="entity.validation.required">
                                    This field is required.
                                </small>
                                <small class="form-text text-danger" *ngIf="editForm.get('titulo').errors.minlength"
                                    jhiTranslate="entity.validation.minlength" [translateValues]="{ min: 3 }">
                                    This field is required to be at least 3 characters.
                                </small>
                                <small class="form-text text-danger" *ngIf="editForm.get('titulo').errors.maxlength"
                                    jhiTranslate="entity.validation.maxlength" [translateValues]="{ max: 15 }">
                                    This field cannot be longer than 15 characters.
                                </small>
                            </div>
                        </div>
                    </div>
                </div>

                <div>
                    <small class="form-text text-primary" jhiTranslate="bestMealApp.pessoa.endereco">
                        Dados do Endereço:
                    </small>
                    <hr class="half-rule" />
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.cep"
                            for="field_cep">Cep</label>
                        <input type="text" class="form-control" name="cep" id="field_cep" formControlName="cep" />
                        <div
                            *ngIf="editForm.get('cep').invalid && (editForm.get('cep').dirty || editForm.get('cep').touched)">
                            <small class="form-text text-danger" *ngIf="editForm.get('cep').errors.pattern"
                                jhiTranslate="entity.validation.pattern" [translateValues]="{ pattern: 'Cep' }">
                                This field should follow pattern for "Cep".
                            </small>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.tipoLogradouro"
                            for="field_tipoLogradouro">Tipo Logradouro</label>
                        <select class="form-control" name="tipoLogradouro" formControlName="tipoLogradouro"
                            id="field_tipoLogradouro">
                            <option value="RUA">{{'bestMealApp.TipoLogradouro.RUA' | translate}}</option>
                            <option value="AVENIDA">{{'bestMealApp.TipoLogradouro.AVENIDA' | translate}}</option>
                            <option value="TRAVESSA">{{'bestMealApp.TipoLogradouro.TRAVESSA' | translate}}</option>
                            <option value="QUADRA">{{'bestMealApp.TipoLogradouro.QUADRA' | translate}}</option>
                            <option value="BECO">{{'bestMealApp.TipoLogradouro.BECO' | translate}}</option>
                            <option value="ESTRADA">{{'bestMealApp.TipoLogradouro.ESTRADA' | translate}}</option>
                            <option value="CHACARA">{{'bestMealApp.TipoLogradouro.CHACARA' | translate}}</option>
                            <option value="RODOVIA">{{'bestMealApp.TipoLogradouro.RODOVIA' | translate}}</option>
                            <option value="VIADUTO">{{'bestMealApp.TipoLogradouro.VIADUTO' | translate}}</option>
                            <option value="SITIO">{{'bestMealApp.TipoLogradouro.SITIO' | translate}}</option>
                            <option value="FEIRA">{{'bestMealApp.TipoLogradouro.FEIRA' | translate}}</option>
                            <option value="SETOR">{{'bestMealApp.TipoLogradouro.SETOR' | translate}}</option>
                            <option value="MORRO">{{'bestMealApp.TipoLogradouro.MORRO' | translate}}</option>
                            <option value="LARGO">{{'bestMealApp.TipoLogradouro.LARGO' | translate}}</option>
                            <option value="FAZENDA">{{'bestMealApp.TipoLogradouro.FAZENDA' | translate}}</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.nomeLogradouro"
                        for="field_nomeLogradouro">Nome Logradouro</label>
                    <input type="text" class="form-control" name="nomeLogradouro" id="field_nomeLogradouro"
                        formControlName="nomeLogradouro" />
                    <div
                        *ngIf="editForm.get('nomeLogradouro').invalid && (editForm.get('nomeLogradouro').dirty || editForm.get('nomeLogradouro').touched)">
                        <small class="form-text text-danger" *ngIf="editForm.get('nomeLogradouro').errors.required"
                            jhiTranslate="entity.validation.required">
                            This field is required.
                        </small>
                        <small class="form-text text-danger" *ngIf="editForm.get('nomeLogradouro').errors.minlength"
                            jhiTranslate="entity.validation.minlength" [translateValues]="{ min: 5 }">
                            This field is required to be at least 5 characters.
                        </small>
                        <small class="form-text text-danger" *ngIf="editForm.get('nomeLogradouro').errors.maxlength"
                            jhiTranslate="entity.validation.maxlength" [translateValues]="{ max: 100 }">
                            This field cannot be longer than 100 characters.
                        </small>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.complemento"
                            for="field_complemento">Complemento</label>
                        <input type="text" class="form-control" name="complemento" id="field_complemento"
                            formControlName="complemento" />
                        <div
                            *ngIf="editForm.get('complemento').invalid && (editForm.get('complemento').dirty || editForm.get('complemento').touched)">
                            <small class="form-text text-danger" *ngIf="editForm.get('complemento').errors.required"
                                jhiTranslate="entity.validation.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" *ngIf="editForm.get('complemento').errors.minlength"
                                jhiTranslate="entity.validation.minlength" [translateValues]="{ min: 0 }">
                                This field is required to be at least 0 characters.
                            </small>
                            <small class="form-text text-danger" *ngIf="editForm.get('complemento').errors.maxlength"
                                jhiTranslate="entity.validation.maxlength" [translateValues]="{ max: 30 }">
                                This field cannot be longer than 30 characters.
                            </small>
                        </div>
                    </div>

                    <div class="form-group col-md-6">
                        <label class="form-control-label" jhiTranslate="bestMealApp.pessoa.municipio"
                            for="field_municipio">Municipio</label>
                        <select class="form-control" id="field_municipio" name="municipio" formControlName="municipio">
                            <option [ngValue]="null"></option>
                            <option
                                [ngValue]="municipioOption.id === editForm.get('municipio').value?.id ? editForm.get('municipio').value : municipioOption"
                                *ngFor="let municipioOption of municipios; trackBy: trackMunicipioById">
                                {{municipioOption.nomeMunicipio+"-"+municipioOption.uf}}</option>
                        </select>
                    </div>
                </div>
            </div>

            <div>
                <button type="button" id="cancel-save" class="btn btn-secondary" (click)="previousState()">
                    <fa-icon [icon]="'ban'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.cancel">Cancel</span>
                </button>
                <button type="submit" id="save-entity" [disabled]="editForm.invalid || isSaving"
                    class="btn btn-primary">
                    <fa-icon [icon]="'save'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.save">Save</span>
                </button>
            </div>
        </form>
    </div>
</div>


```

<p align="center">
   <strong>Listagem 3 - `pessoa-update.component.html` </strong> 
</p>


5. Testando a aplicação

<p align="center">
  <img src="images/Pessoas.png" alt="Novo lay-out do formulário pessoas">
</p>
<p align="center">
   <strong>Figura 1- Novo lay-out do formulário pessoas</strong> 
</p>
