# Read Me First

Mini projeto de desafio para ingressar na compasso uol do candido Raul Sousa.

### Requisitos:

- JAVA 11
- Maven 3.6.3 ou superior
- Docker (Opcional)

# Getting Started

### Iniciando locamente utilizando o maven e java

O projeto pode ser iniciado utilizando os seguintes comandos:

- mvn clean install 
- java -jar target/product-ms-1.0.0.jar

### Iniciando via Docker

basta executar o script: ./run.sh Esse comando irá realizar o build do .jar e do container e em seguinda executa-lo na porta 9999

# Testando o projeto

### Swagger
[http://localhost:9999/swagger-ui.html](http://localhost:9999/swagger-ui.html)

### EndPoints
- GET http://localhost:9999/products
- GET http://localhost:9999/products/{id}
- GET http://localhost:9999/products/search
- POST http://localhost:9999/products
- PUT http://localhost:9999/products/{id}
- DELETE http://localhost:9999/products/{id}

#Referências
[https://bitbucket.org/RecrutamentoDesafios/desafio-java-springboot/src/master/](https://bitbucket.org/RecrutamentoDesafios/desafio-java-springboot/src/master/)


