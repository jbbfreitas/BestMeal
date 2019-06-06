# BestMeal - Validações Assíncronas

## O que são validações assíncronas?

> As validações assíncronas, diferentemente das síncronas não são realidas no cliente, ocorrem no servidor. São validações que envolvem regras de negócio que dependem de uma API externa. Um caso de uso é, por exemplo, quando queremos verificar se um CPF ou CNPJ já está em uso.


> Recebem esse nome por que a resposta da validação geralmente é uma `Promise` ou um `Observeable`

> Por razões de desempenho as validações assíncronas semente serão invocadas quando todos as va;idações síncronas passarem pelo teste. Se pelo menos uma função de validação de sincronização retornar com falha, nenhum validador assíncrono será chamado.


### Implementando uma validação `assíncrona` na aplicação BestMeal

> A validação que iremos implementar, impede que uma determinado cpf seja reutilizado em outra pessoa. Em outras palavras, um cpf só poderá ser utilizado uma única vez! O mesmo vale para o cn pj.


#### Implementações do lado cliente

::: :walking: Passo a passo :::

1. Iremos utilizar uma nova classe de serviço para fazer essas validações. Essa classe ficará na pasta `src/main/webapp/app/shared/validators` e será denominada  `cpf-cnpj-validators.service.ts`

2. Ness pasta, crie um novo arquivo denominado `cpf-cnpj-validators.service.ts`, conforme Listagem 1

```typescript
import { Injectable } from '@angular/core';
import { PessoaService } from 'app/entities/pessoa';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { IPessoa } from '../model/pessoa.model';
import { AsyncValidatorFn, AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';

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
        if (
          cnpj === '00000000000000' ||
          cnpj === '11111111111111' ||
          cnpj === '22222222222222' ||
          cnpj === '33333333333333' ||
          cnpj === '44444444444444' ||
          cnpj === '55555555555555' ||
          cnpj === '66666666666666' ||
          cnpj === '77777777777777' ||
          cnpj === '88888888888888' ||
          cnpj === '99999999999999'
        ) {
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
        resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
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
        resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        if (resultado !== Number(digitos.charAt(1))) {
          return { cnpjNotValid: { value: control.value } };
        }

        return null;
      }
    };
  }
  /**
   *
   * @param pessoaService
   * Test if cpf is already in use by another person
   */

  existingCpfValidator(pessoaService: PessoaService): AsyncValidatorFn {
    return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
      return pessoaService
        .findWithCpf(control.value)
        .pipe(map(res => (control.value && res && res.body > 0 ? { cpfAlreadyInUse: { value: control.value } } : null)));
    };
  }

  /**
   *
   * @param pessoaService
   */
  existingCnpjValidator(pessoaService: PessoaService): AsyncValidatorFn {
    return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
      return pessoaService
        .findWithCnpj(control.value)
        .pipe(map(res => (control.value && res && res.body > 0 ? { cnpjAlreadyInUse: { value: control.value } } : null)));
    };
  }

}
```

<p align="center">
   <strong>Listagem 1 - cpf-cnpj-validators.service.ts </strong> 
</p>

::: :pushpin: Importante :::

> Observe que os métodos `isValidCpf()` e `isValidCnpj():` retornam um `ValidatorFn`. Isso significa que eles serão utilizados em validações síncronas. Por outro lado os métodos `existingCpfValidator` e `existingCnpjValidator` retornam uma função do tipo `AsyncValidatorFn`, significando que eles serão utilizados em validações assíncronas.


> Os dois primeiros métodos fazem validação no dígito verificador e retornam ou um json ou null.
Se retornarem um json (do tipo `{ cnpjNotValid: { value: control.value } }`) significa que houve erro, o qual poderá ser tratado na view usando, por exemplo, a chave `cnpjNotValid`.

> Os dois últimos métodos validam no servidor. Para isso usam um outro serviço `pessoaService.findWithCnpj(control.value)` ou `pessoaService.findWithCpf(control.value)`. Por que eles fazem uso de um outro service? A resposta é simples: o service já contém os métodos `http` para estabelecer a comunicação com o servidor. Por isso iremos apenas adicionar dois métodos a essa classe, conforme o item 3.


3. Altere o arquivo denominado `pessoa.service.ts`, adiconando os 3 métodos no final da classe, conforme Listagem 2

```typescript
...
// Aqui a classe anterior
// Adicione os 3 métodos abaixo à classe
...

findWithCpf(cpf: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.pessoa && this.pessoa.id ? String(this.pessoa.id) : '0';
    parametros = parametros.set('cpf', cpf);
    parametros = parametros.append('id', id);
    const withcpf = 'withcpf';
    const url = `${this.resourceUrl}/${withcpf}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  findWithCnpj(cnpj: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.pessoa && this.pessoa.id ? String(this.pessoa.id) : '0';
    parametros = parametros.set('cnpj', cnpj);
    parametros = parametros.append('id', id);
    const withcnpj = 'withcnpj';
    const url = `${this.resourceUrl}/${withcnpj}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  setPessoa(pessoa: IPessoa) {
    this.pessoa = pessoa;
  }
  ```

<p align="center">
   <strong>Listagem 2 - pessoa.service.ts </strong> 
</p>

::: :pushpin: Importante :::

> Observe que esses métodos retornam um tipo `Observeable`, conforme dissemos anteriormente, como condição para que a validação seja assíncrona.

> Esses dois métodos invocam dois métodos `REST` :
-  `api/pessoas/withcnpj` e 
-  `api/pessoas/withcpf` 

> Se você der uma olhada na Listagem 9, verá que esses dois métodos recebem um parâmetro multivalorado do tipo `@RequestParam MultiValueMap<String, String> queryParams`. Essa é uma das formas de passar parâmetros multivalorados para um método `http.GET`.

> Por que parâmetros multivalorados? Ocorre que para fazer a validação do cnpj ou cpf únicos, precisamos verificar se o cpf/cnpj está sendo utilizado por outra pessoa (com um `id` diferente do informado). Esta é a razão pela qual temos que passar dois parâmetros: o `cpf/cnpj` e o `id`. 

> Observe o trecho de código que faz a montagem dos parâmetros:

```typescript
    let parametros: HttpParams = new HttpParams();
    ...
    parametros = parametros.set('cnpj', cnpj);
    parametros = parametros.append('id', id);
```


4. Altere o arquivo denominado `pessoa-update.component.ts`, conforme Listagem 3

```typescript
    cpf: [
      null,
      [Validators.pattern('^[0-9]{3}.?[0-9]{3}.?[0-9]{3}-?[0-9]{2}$'), this.customCPFCNPJValidatorService.isValidCpf()], // >> Adicionada
      [this.customCPFCNPJValidatorService.existingCpfValidator(this.pessoaService)] // >> Adicionada
     ],
  cnpj: [
      null,
      [Validators.pattern('^[0-9]{2}.?[0-9]{3}.?[0-9]{3}/?[0-9]{4}-?[0-9]{2}$'), this.customCPFCNPJValidatorService.isValidCnpj()], // >> Adicionada
      [this.customCPFCNPJValidatorService.existingCnpjValidator(this.pessoaService)] // >> Adicionada
    ],
```

```typescript 

    nomeMeio: [], // >> alterada
    sobreNome: [], // >> alterada
    titulo: [], // >> alterada

```

```typescript
// >> Método adicionado
setPessoaFisicaValidity() {
    const nomeMeio = this.editForm.get('nomeMeio');
    const sobreNome = this.editForm.get('sobreNome');
    const titulo = this.editForm.get('titulo');
    this.editForm.get('tipo').valueChanges
      .subscribe(tipo => {

        if (tipo === 'FISICA') {
          nomeMeio.setValidators([Validators.required, Validators.minLength(2), Validators.maxLength(30)]);
          sobreNome.setValidators([Validators.required, Validators.minLength(2), Validators.maxLength(30)]);
          titulo.setValidators([Validators.required, Validators.minLength(3), Validators.maxLength(15)]);

        } else {
          nomeMeio.setValidators([]);
          sobreNome.setValidators([]);
          titulo.setValidators([]);
          nomeMeio.setValue(null);
          sobreNome.setValue(null);
          titulo.setValue(null);

        }
        nomeMeio.updateValueAndValidity();
        sobreNome.updateValueAndValidity();
        titulo.updateValueAndValidity();

      });
  }
```

```typescript
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
     this.setPessoaFisicaValidity(); // >> adicionado
  }

```
<p align="center">
   <strong>Listagem 3 - `pessoa-update.component.ts` </strong> 
</p>

5. Altere o arquivo denominado `pessoa-update.componente.html`, conforme Listagem 4. Como são diversas alterações, será mais fácil copiar o arquivo todo (Listagem 4) 

```typescript
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
                                    <small class="form-text text-danger"
                                        *ngIf="editForm.get('cpf').errors.cpfAlreadyInUse"
                                        jhiTranslate="entity.validation.cpfAlreadyInUse">
                                        CPF já está em uso.
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
                                        Digito do CNPJ é inválido.
                                    </small>

                                    <small class="form-text text-danger"
                                        *ngIf="editForm.get('cnpj').errors.cnpjAlreadyInUse"
                                        jhiTranslate="entity.validation.cnpjAlreadyInUse">
                                        CNPJ já está em uso.
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
   <strong>Listagem 4 -arquivo `pessoa-update.componente.html`</strong> 
</p>

6. Altere o arquivo `global.json`, conforme abaixo:

```json
      "validadeCart": "Mês e ano tem que ter um valor maior ou igual a {{mesanoMin}}",
      "cpfdigit": "Digito do CPF é inválido", // adicionado
      "cnpjdigit": "Digito do CNPJ é inválido", // adicionado
      "cpfAlreadyInUse": "CPF já está em uso por outra pessoa", // adicionado
      "cnpjAlreadyInUse": "CNPJ já está em uso por outra pessoa" // adicionado
```

#### Implementações do lado servidor

::: :walking: Passo a passo :::

1. Iremos alterar 5 classes Java, são elas: `Pessoa.java`, `PessoaRepository.java`, `PessoaService.java`, `PessoaServiceImpl.java`  e `PessoaResource.java`

2. Vamos começar por `Pessoa.java`. 

> Essa é a classe de domínimo. Nela  teremos que retirar as validações do lado servidor, visto que no cliente, ora serão informados dados de pessoa física, ora dados de pessoa jurídica. Portanto os campos que não são comuns a ambos os tipo (Física e Jurídica), não podem ser obrigatórios. Lembre-se também de verificar se na base de dados, a tabela Pessoa não contém obrigatoriedade nesses campos. Se tiver, retire essa obrigatoriedade.

```java
    // >> alterados
    @Size(min = 2, max = 30)
    @Column(name = "nome_meio", length = 30)
    private String nomeMeio;

    @Size(min = 2, max = 30)
    @Column(name = "sobre_nome", length = 30)
    private String sobreNome;


    @Size(min = 3, max = 15)
    @Column(name = "titulo", length = 15)
    private String titulo;

```
<p align="center">
   <strong>Listagem 5 -arquivo `Pessoa.java`</strong> 
</p>

3. Alterações em `PessoaRepository.java`. 

> Essa é a uma interface onde iremos criar 4 métodos para contar o número de pessoas com o mesmo cpf/cnpj. A anotação `@Query` detalha a `JPQL-JPA Query Language` que será utilizada no método. Observe que os dois primeiros métodos recebem dois parâmetros enquanto que os dois últimos, apenas um.

```java
    // >> adicionar
    @Query("SELECT count(p) FROM Pessoa p WHERE p.cpf = :cpf and p.id <> :id")
	Long countWithCpf(@Param("cpf") String cpf, @Param("id") Long id);

    @Query("SELECT count(p) FROM Pessoa p WHERE p.cnpj = :cnpj and p.id <> :id")
	Long countWithCnpj(@Param("cnpj") String cnpj, @Param("id") Long id);

    @Query("SELECT count(p) FROM Pessoa p WHERE p.cpf = :cpf ")
	Long countWithCpfIdNull(@Param("cpf") String cpf);

    @Query("SELECT count(p) FROM Pessoa p WHERE p.cnpj = :cnpj ")
	Long countWithCnpjIdNull(@Param("cnpj") String cnpj);

```
<p align="center">
   <strong>Listagem 6 - arquivo `PessoaRepository.java`</strong> 
</p>

4. Alterações em `PessoaService.java`. 

> Essa é a uma interface onde iremos criar a assinatura de  e métodos. Por que usar uma interface? Lembre-se: 'Programe por interfaces".


```java
    // >> adicionar
	Long countWithCpf(String cpf, Long id);
	Long countWithCnpj(String cnpj, Long id);
	Long countWithCpf(String cpf);
	Long countWithCnpj(String cnpj);
```
<p align="center">
   <strong>Listagem 7 - arquivo `PessoaService.java`</strong> 
</p>

5. Alterações em `PessoaServiceImpl.java`. 

> Essa é a classe que implementa a interface `PessoaService.java`, portanto, é aqui que iremos dar corpo aos 4 métodos anteriores.


```java
    // >> adicionar
    @Override
    public Long countWithCpf(String cpf, Long id) {
        return pessoaRepository.countWithCpf(cpf,id);
    }

    @Override
    public Long countWithCnpj(String cnpj, Long id) {
        return pessoaRepository.countWithCnpj(cnpj,id);
    }

    @Override
    public Long countWithCpf(String cpf) {
        return pessoaRepository.countWithCpfIdNull(cpf);
    }

    @Override
    public Long countWithCnpj(String cnpj) {
        return pessoaRepository.countWithCnpjIdNull(cnpj);
    }
```
<p align="center">
   <strong>Listagem 8 - arquivo `PessoaServiceImpl.java`</strong> 
</p>

6. Alterações em `PessoaResource.java`. 

> Essa é a classe que implementa a interface `PessoaService.java`, portanto, é aqui que iremos dar corpo aos 4 métodos anteriores.


```java
    // >> adicionar
    @GetMapping("/pessoas/withcpf")
    public ResponseEntity<Long> countAllPessoasWithCPF(@RequestParam MultiValueMap<String, String> queryParams) {
        log.debug("REST request to get Pessoas with  same CPF");
        log.debug("--->O valor do id é:" + queryParams.get("id").get(0));
        Long numero = 0L;
        try {
            String cpf = queryParams.get("cpf").get(0);
            Long id = Long.parseLong(queryParams.get("id").get(0));
            log.debug("REST request to get Pessoas with  same CPF");
            if (id == 0) {
                numero = pessoaService.countWithCpf(cpf);
            } else {
                numero = pessoaService.countWithCpf(cpf, id);
            }
        } catch (Exception e) {
            numero = 0L;
        }
        return ResponseEntity.ok(numero);
    }

    @GetMapping("/pessoas/withcnpj")
    public ResponseEntity<Long> countAllPessoasWithCNPJ(@RequestParam MultiValueMap<String, String> queryParams) {
        log.debug("REST request to get Pessoas with  same CNPJ");
        log.debug("--->O valor do id é:" + queryParams.get("id").get(0));
        Long numero = 0L;
        try {
            String cnpj = queryParams.get("cnpj").get(0);
            Long id = Long.parseLong(queryParams.get("id").get(0));
            log.debug("REST request to get Pessoas with  same CNPJ");
            if (id == 0) {
                numero = pessoaService.countWithCnpj(cnpj);
            } else {
                numero = pessoaService.countWithCnpj(cnpj, id);
            }
        } catch (Exception e) {
            numero = 0L;
        }
        return ResponseEntity.ok(numero);
    }

```
<p align="center">
   <strong>Listagem 9 - arquivo `PessoaResource.java`</strong> 
</p>

7. Pronto! No lado servidor faça `mvnw clean` e depois `mvnw`. Do lado cliente faça `npm start`. 