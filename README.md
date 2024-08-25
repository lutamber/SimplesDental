<img alt="image" src="https://www.simplesdental.com/wp-content/uploads/2018/02/simplesdental-logo.png" width="200"/>

# Teste Prático Dev (Back-End Java)
### Teste técnico para empresa Simples Dental feito em Java com framework Spring

Com o projeto já baixado será necessário executar alguns passos (simples e rápidos):
- Inicialize seu docker para que possamos criar nosso banco de dados
- Agora na pasta do projeto basta digitar o comando <code>docker-compose up -d</code>
- Com o container rodando, basta rodar o projeto na IDE de sua preferência :)

<sub>Para realizar requisições na API via Postman o arquivo <b>SimplesDental.postman_collection</b> contendo a collection de requests da API esta disponível na raiz do projeto.</sub>

# Tecnologias Utilizadas
- Spring Boot v3.3.3
- Java 21
- PostgreSQL
- Docker
- JUnit
- Swagger
- Lombok
- Hibernate
- H2
- Postman

# API Profissionais
<sub>Para mais detalhes verifique a documentação que estará disponível em http://localhost:8080/swagger-ui/index.html</sub>
<table>
    <tr>
        <td>Método</td>
        <td>Rota</td>
        <td>Ação</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/profissionais</td>
        <td>Listar profissionais</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/profissionais/{id}</td>
        <td>Retorna profissional pelo ID</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/profissionais</td>
        <td>Inserir novo profissional</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/profissionais/{id}</td>
        <td>Atualizar um profissional pelo ID</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/profissionais/{id}</td>
        <td>Deletar (lógicamente) um profissional pelo ID</td>
    </tr>
</table>

# API Contatos
<sub>Para mais detalhes verifique a documentação que estará disponível em http://localhost:8080/swagger-ui/index.html</sub>
<table>
    <tr>
        <td>Método</td>
        <td>Rota</td>
        <td>Ação</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/contatos</td>
        <td>Lista todos os contatos</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/contatos/{id}</td>
        <td>Retorna contato pelo ID</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/contatos</td>
        <td>Inserir novo contato</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/contatos/{id}</td>
        <td>Atualizar um contato pelo ID</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/contatos/{id}</td>
        <td>Deletar um contato pelo ID</td>
    </tr>
</table>