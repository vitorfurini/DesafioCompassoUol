<h2>Desafio Técnico CompassoUol</h2>

Este desafio foi realizado para fins de teste técnico para empresa CompassoUOL

<h2> Descrição</h2>
O projeto foi desenvolvido para gerenciar produtos na base dadados. Foram criados endpoints para criação, edição, deleção, busca por id, busca por filtros.

As tecnologias utilizadas foram:
- Java 11
- Spring Boot
- Maven
- Junit 5
- H2
- Swagger

<h2>Execução do projeto</h2>

Para compilar o projeto, execute o camando: mvn clean install

Para executar o projeto, execute o camando: mvn spring-boot:run

Para acessar os endpoints criados, acesse: http://localhost:9999/swagger-ui.html#/

A lista de requisições que podem ser feitas no projeto são:

- POST /products
- PUT /products/{id}
- GET /products/{id}
- GET /products
- GET /products/search
- DELETE /products/{id}


Para mais detalhes, acessar: https://bitbucket.org/RecrutamentoDesafios/desafio-java-springboot/src/master/
