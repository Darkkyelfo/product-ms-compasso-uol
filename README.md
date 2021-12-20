# Read Me First

Mini projeto de desafio para ingressar na compasso uol do candido Raul Sousa. Consiste em uma API Rest para catálogo de
produtos.

### Requisitos:

- JAVA 11
- Maven 3.6.3 ou superior
- Porta 9999 disponível
- Docker (Opcional)

# Getting Started

### Iniciando localmente utilizando o maven e java

O projeto pode ser iniciado utilizando os seguintes comandos:

Para realizar o build do projeto:

```shell
mvn clean install
```

Para executar o projeto:

```shell
mvn clean package spring-boot:run
```

O processo será executado na porta 9999

### Iniciando via Docker

Para executar aplicação dentro de um container docker basta executar o script:

```shell
./run.sh
```

Esse script irá realizar um build do projeto e em seguida "containizar" aplicação e executa-la dentro do container.

# Testando o projeto

### Swagger

[http://localhost:9999/swagger-ui.html](http://localhost:9999/swagger-ui.html)

### H2-console

[http://localhost:9999/h2-console](http://localhost:9999/h2-console)

### EndPoints

- GET http://localhost:9999/products
- GET http://localhost:9999/products/{id}
- GET http://localhost:9999/products/search
- POST http://localhost:9999/products
- PUT http://localhost:9999/products/{id}
- DELETE http://localhost:9999/products/{id}

# Referências

[https://bitbucket.org/RecrutamentoDesafios/desafio-java-springboot/src/master/](https://bitbucket.org/RecrutamentoDesafios/desafio-java-springboot/src/master/)

### Autor: Raul Pedro de Vasconcelos Sousa
