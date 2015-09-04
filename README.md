# rest-api-endereco

### Do que se trata esse Repositório ###

Exemplo de API Rest para gerenciamento endereço. Provê serviços de Busca de Endereço
Global por CEP e CRUD de Endereço de Cliente.

## Arquitetura ##

A arquitetura utilizada é baseada na Layered Architecture, com alguns conceitos de
DDD, como a proposto por Eric Evans no projeto DDD Sample [here](http://dddsample.sourceforge.net/architecture.html)
Arquitetura organiza o software de forma bem simples em três camadas horizontais 
suportadas por uma quarta camada vertical de infraestrutura. 

### Tecnologias ###

* Spring Boot com Maven
* Jax-RS fornecido pelo Jersey para os serviços REST
* Spring data JPA para persistir num banco relacional (H2 em memória foi o escolhido)
* JDK 1.7+
* Groovy 2.4 é necessário para execução dos testes BDD que foram escritos usando Spock 
* Tomcat Embarcado

### Pré Requisitos ###
* Git
* JDK 1.7+
* Maven
* Groovy 2.4

### Banco de Dados ###
- Foi utilizado H2 em memória para Desenvolvimento e Testes. 
- Os dados utilizados na busca de CEP, que representam endereçoes globais não necessariamente
associados a clientes, estão estáticos na aplicação em um MAP. Os CEPs disponíveis para busca são:
"10000000", "13000000", "13100000", "13040000", "13045000", "13046100", "13047120", "13045909"

### Executar localmente ###
> mvn package -DskipTests && java -jar target/rest-api-endereco-0.1.0.jar

### Executar testes ###
> mvn test

## Exemplos de uso da Api ##

### Endereço Geral ###

#### Buscar endereço por CEP ####

* http GET - http://localhost:8080/api/enderecos?cep=13045909

* retorna:
```
#!json
{
  "cep": {
    "codigo": "13045909"
  },
  "rua": "Rua do cep 13045909",
  "bairro": "Bairro do cep 13045909",
  "cidade": "Cidade do cep 13045909",
  "estado": "Estado do cep 13045909"
}
```
* retorna http 404 se não encontrar endereço para o CEP
* retorna http 400 se o CEP for inválido

### CRUD de endereço do cliente ###

#### Criar um novo endereço ####

* http POST - http://localhost:8080/api/enderecoscliente
* json body:
```
#!json
{
  "cep": {
    "codigo": "13045760"
  },
  "rua": "Rua Martinho Calsavara",
  "numero": 192,
  "complemento": "Ap. D96",
  "bairro": "Swift",
  "cidade": "Campinas",
  "estado": "SP"
}
```
* returna http 200 com o endereço criado:
```
#!json
{
  "id": 1,
  "cep": {
    "codigo": "13045760"
  },
  "rua": "Rua Martinho Calsavara",
  "numero": 192,
  "complemento": "Ap. D96",
  "bairro": "Swift",
  "cidade": "Campinas",
  "estado": "SP"
}
```
* retorna http 400 se violar alguma regra de obrigatoriedade ou formato.

### Buscar endereço por Id ###

* http GET - http://localhost:8080/api/enderecoscliente/1

* retorna:
```
#!json
{
  "id": 1,
  "cep": {
    "codigo": "13045760"
  },
  "rua": "Rua Martinho Calsavara",
  "numero": 192,
  "complemento": "Ap. D96",
  "bairro": "Swift",
  "cidade": "Campinas",
  "estado": "SP"
}
```
* retorna http 404 se não encontrar endereço do cliente

### Atualizar endereço existente ###

* http PUT - http://localhost:8080/api/enderecoscliente/1
* json body:
```
#!json
{
  "cep": {
    "codigo": "13045760"
  },
  "rua": "Rua Martinho Calsavara",
  "numero": 200,
  "complemento": "Ap. D96",
  "bairro": "Swift",
  "cidade": "Campinas",
  "estado": "SP"
}
```
* retorna http 200 com o endereço atualizado
* retorna http 404 se não encontrar endereço do cliente
* retorna http 400 se violar alguma regra de obrigatoriedade ou formato.
```
#!json
{
  "id": 1,
  "cep": {
    "codigo": "13045760"
  },
  "rua": "Rua Martinho Calsavara",
  "numero": 200,
  "complemento": "Ap. D96",
  "bairro": "Swift",
  "cidade": "Campinas",
  "estado": "SP"
}
```

### Excluir um endereço ###

* http DELETE - http://localhost:8080/api/enderecoscliente/1

* returna http 204
* retorna http 404 se não encontrar endereço do cliente
