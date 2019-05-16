# BestMeal - Validações

## Por que Validar no lado cliente?

> A validação do lado do cliente fornece ao usuário um feedback imediato, sem ter que esperar o carregamento da página. As aplicações cliente modernas fazem a validação imediata das entradas de dados, ou seja, à medida que os dados são digitados por você. Antigamente era preciso teclar um botão `gravar` ou `enviar` para que o lado cliente informasse quais os campos estavam inconsistentes. Infelizmente muitas aplicações ainda são até hoje!

## A validação do lado cliente é suficente?

> Infelizmente não. Se o browser tiver os scripts desabilitado (por exemplo, JavaScript desabilitado), a validação não será disparada, e é por isso que você precisa do servidor para verificar os valores também.

## Quais os tipos de validação são utilizadas no Angular?

> O Angular permite duas formas de validação em formulários: `Template-driven` e `Reactive Forms`.

### Template-driven

> `Template-driven` usa atributos de validação exatamente como você como faria com a validação de formulário HTML nativo. Angular usa diretivas para combinar esses atributos com funções validadoras no framework. Toda vez que o valor de um controle de formulário for alterado, o Angular executa a validação e gera uma lista de erros de validação, que resulta em um status INVALID ou nulo.

### Exemplo de `Template-drive`

```typescript
<input id="name" name="name" class="form-control"
      required minlength="4" appForbiddenName="bob"
      [(ngModel)]="hero.name" #name="ngModel" >

<div *ngIf="name.invalid && (name.dirty || name.touched)"
    class="alert alert-danger">

  <div *ngIf="name.errors.required">
    Name is required.
  </div>
  <div *ngIf="name.errors.minlength">
    Name must be at least 4 characters long.
  </div>
  <div *ngIf="name.errors.forbiddenName">
    Name cannot be Bob.
  </div>

</div>
```

<p align="center">
   <strong>Listagem 1- Exemplo de validação usando Template-drive</strong> 
</p>

### Reactive Forms

> `Reactive Forms` Nessa modalidade, em vez de adicionar validadores por meio de atributos no modelo, você adiciona funções do validador diretamente ao modelo de controle de formulário na classe do componente. Angular então chama essas funções sempre que o valor do controle é alterado.
> Funções do validador

Existem dois tipos de funções validadoras: validadores síncronos e validadores assíncronos.

    Validadores síncronos: retornam imediatamente um conjunto de erros de validação ou nulo.

    Validadores assíncronos: retornam um Promise ou Observable que posteriormente emite um conjunto de erros de validação ou null.

> Nota 1: por motivos de desempenho, o Angular só executa validadores assíncronos se todos os validadores de síncronos passarem. Cada um deve concluir antes que os erros sejam definidos.

> Nota 2: o Jhipster a partir da versão 6.X usa `Reactive Forms` por padrão.

#### Implementando um `Reactive Forms` na aplicação BestMeal

::: :walking: Passo a passo :::

1. Cria a estrutura de pastas conforme Figura 1

<p align="center">
  <img src="images/EstruturaValidators.png" alt="Estrutura de pastas para validadores">
</p>
<p align="center">
   <strong>Figura 1- Imagem da estrutura de pastas para validadores</strong> 
</p>
