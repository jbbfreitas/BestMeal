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

2. Execute a seguinte instrução no prompt de comandos, para o jhipster gerar essa entidade:

```prompt
jhipster entity Cliente
```



