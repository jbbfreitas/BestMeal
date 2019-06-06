# BestMeal - Formulário Mestre - Detalhe

## O que são formulários mestre-detalhe?

>São formulários que contém uma instância de uma classe principal (por exemplo `Cliente`) e da qual dependem uma ou mais classes (por exemplo `CartãoCredito`). Se pensarmos em um modelo de classes da UML, podederíamos dizer que são a representação dos relacionamentos 1:N.

>No nosso estudo de caso queremos que, ao selecionar um Cliente, sejam exibidos os seus Cartões de Créditos (um ou mais).

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
4. Faça as seguintes alterações, conforme listagem 3 a 

```typescript

<!-- Exclua as linhas abaixo -->
              <div class="form-group">
                    <label class="form-control-label" jhiTranslate="bestMealApp.cartaoCredito.cliente" for="field_cliente">Cliente</label>
                    <select class="form-control" id="field_cliente" name="cliente" formControlName="cliente">
                        <option [ngValue]="null"></option>
                        <option [ngValue]="ClienteOption.id === editForm.get('cliente').value?.id ? editForm.get('cliente').value : ClienteOption" *ngFor="let ClienteOption of clientes; trackBy: trackClienteById">{{ClienteOption.primeiroNome}}</option>
                    </select>
                </div>
```

<p align="center">
   <strong>Listagem 3 - cartao-credito-update.component.html </strong> 
</p>

```typescript

<!-- Exclua as linhas abaixo -->
```

<p align="center">
   <strong>Listagem 4 - cartao-credito-update.component.html </strong> 
</p>

