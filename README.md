# BestMeal

Esta aplicação faz reservas em restaurantes.

## Desenvolvimento

1. Crie uma pasta denominada `BestMeal` sob a pasta `Grupo de Estudos`

2. Nessa pasta, execute o tutorial do Jhipster conforme https://github.com/jbbfreitas/jhipster , lembre-se de alterar o nome da aplicação para `BestMeal` e o pacote base para `br.com.abim.bestmeal`

3. No pgadmin crie um usuário denominado `bestmeal` e senha `bestmeal`

4. No pgadmin crie um database denominado `bestmeal` com owner `bestmeal`

5. Importe o projeto no Eclipse

6. Altere o aquivo `/src/main/resources/config/application-dev.yml` conforme abaixo

```yml
datasource:
  type: com.zaxxer.hikari.HikariDataSource
  url: jdbc:postgresql://localhost:5432/bestmeal
  username: bestmeal
  password: bestmeal
---
server:
  port: 8090
```

7. Salve o arquivo alterado e execute a aplicação, digitando no promtp de comando:

```
mvn
```

8. Abra o browser e digite a url : `localhost:8090``

9. Acesse a aplicação com usuario:admin e senha:admin

### Uma segunda opção de inicialização com o npm independente

1. Altere o arquivo `BestMeal/webpack/webpack.dev.js`para um novo endereco de proxy, conforme abaixo

```js
        proxy: [{
            context: [
                /* jhipster-needle-add-entity-to-webpack - JHipster will add entity api paths here */
                '/api',
                '/management',
                '/swagger-resources',
                '/v2/api-docs',
                '/h2-console',
                '/auth'
            ],
            target: `http${options.tls ? 's' : ''}://127.0.0.1:8090`,
            secure: false,
            changeOrigin: options.tls,
            headers: { host: 'localhost:9000' }
        }],
```

2. Execute os seguintes comandos em dois terminais separados para criar uma experiência melhor de desenvolvimento onde seu navegador
   atualiza automaticamente quando os arquivos são alterados no disco rígido.

```java
    ./mvnw
    npm start
```

### Criação da primeira entidade : CartaoCredito

```js

1.  Digite: jhipster:entity CartaoCredito

2. Gerando o campo #1 ?

Do you want to add a field to your entity? Yes ?
What is the name of your field? bandeira ?
What is the type of your field? Enumeration (Java enum type) ?
What is the class name of your enumeration? Bandeira ?
What are the values of your enumeration (separated by comma, no spaces)? Maste er,Visa,Elo,American,Dinners ?
Do you want to add validation rules to your field? No


3. Gerando o campo #2 ? Do you want to add a field to your entity? Yes ?
What is the name of your field? numero ?
What is the type of your field? String ?
Do you want to add validation rules to your field?

No 4. Gerando o campo #3 ?
Do you want to add a field to your entity? Yes ?
What is the name of your field? cv ?
What is the type of your field? String ?
Do you want to add validation rules to your field? No

5. Gerando o campo #4 ?
Do you want to add a field to your entity? Yes ?
What is the name of your field? validade ?
What is the type of your field? String ?
Do you want to add validation rules to your field? Yes ?
Which validation rules do you want to add? Required, Minimum length, Maximum length ?
What is the minimum length of your field? 4 ?
What is the maximum length of your field? 4 6. ?

Do you want to add a field to your entity? No

7. Generating relationships to other entities
8. ? Do you want to add a relationship to another entity? No

```

### Execute a aplicação e teste o CRUD do `CartaoCredito`

### Abra o modelo de classes para as entidades :

[Modelo de Classes](https://github.com/jbbfreitas/BestMeal/blob/master/Calsses%20do%20restaurante.jpg)

### Usando o jhipster, crie as seguintes entidades :

```
CartaoRecompensa
Menu
Produto
Nome
Logradouro e Município
```
