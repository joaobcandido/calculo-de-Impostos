# Api Calculo de Impostos

Esta é uma API RESTful desenvolvida em Java para calculo de impostos como (ICMS, ISS, IPI).<BR>
A API permite que novos usuários se registrem e que usuários existentes façam login.<br>
Apenas usuarios autenticados podem acessar os endpoints de listar impostos e listar impostos por ID.<BR>
Somente usuarios autenticados com a role ADMIN podem acessar os endpoints de criar,excluir impostos e calcular de impostos.

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Security
- JPA/Hibernate
- Maven
- Swagger
- Containers
## Requisitos
- JDK 11 ou superior
- Maven 3.6+
-  Docker Compose
-  Podman
-  Podman compose

  ##  Executando o projeto localmente
  1 . Clone o repositório:
   ```bash
   git clone git@github.com:joaocandidozup/calculo-de-Impostos.git
  ````
  2 . Construir o projeto::
  ````bash
   mvn clean package
  ````
  3 . Executar com Podman Compose:
  ````yml
   podman-compose up --build
  ````
  O sistema estará disponível em: `http://localhost:8080`
    
  ## Endpoints da API
  Certifique-se de que a aplicação está rodando. Abra o navegador e acesse: http://localhost:8080/swagger-ui/index.html.<br>
  Na interface do Swagger, você pode:

  - Visualizar todas as rotas disponíveis.
  - Testar a API diretamente na interface.
  - OBS: Na intrface ui do swagger precisa abilitar o botao de autorizacao passando o token gerado no login:

   ![image](https://github.com/user-attachments/assets/6a8fd802-1a04-4f20-8a96-cd3130b39edc)

  ## Exemplo de Payloads:
   1 . cadastrar um usuario com role ADMIN:
   ````json
  {
    "username":"joaoAdmin",
    "password": "admin123",
    "role": "ADMIN"
  }
  ````
  2 . fazer login :
  ````json
  {
   "username":"joaoAdmin",
   "password": "admin123"
  }
  ````
  3 . cadastrar um Imposto:
  ````json
    {
   "name": "IPI",
   "description": "Imposto sobre Produtos Industrializados",
   "aliquot": 15.0
  }
  ````

  4 . calcular Imposto:
  ````json
  {
   "baseValue":1000.0,
   "typeTaxId": 1
  }
  ````
  ## Segurança
  A API utiliza JWT (JSON Web Token) para autenticação. Após o login, o cliente deve incluir o token
   JWT no cabeçalho de todas as requisições subsequentes para acessar endpoints protegidos.

  Exemplo de cabeçalho de autorização:
  ````bash
  Authorization: Bearer <jwt-token>
  ````
  ![image](https://github.com/user-attachments/assets/cb655e1c-c10a-43b8-adf8-59eecc57980d)
  ## Testes
   Para rodar os testes unitários, execute o seguinte comando:
    ````bash
    mvn test
    ````
    ou clique com o botão direito na raiz do projeto e escolha RUN ALL TESTS
  
  ---
  
  
