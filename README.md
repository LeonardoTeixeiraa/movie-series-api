# 🎬 movie-series-api
API REST desenvolvida em Spring Boot para buscar séries na OMDb, armazenar em banco de dados e consultar informações sobre séries e episódios.

## Tecnologias

- Java 17+

- Spring Boot

- Spring Web

- Spring Data JPA

- MySQL

- Maven

- OMDb API
  
- Docker

## Funcionalidades:
- Importar séries da OMDb API
- Listar séries cadastradas no banco de dados
- Buscar séries cadastradas no banco de dados por ID ou por titulo
- Top 5 séries por avaliação
- Buscar séries por categoria (ex: terror)
- Listar episódios por temporada
- Top 5 episódios por série
- Episódios mais recentes

## Endpoints:
> GET /series - retorna todas as séries cadastradas no banco
> POST /series/buscar - busca uma série na API OMDb
> GET /series/buscar-por-titulo - busca uma serie no banco pelo parâmetro titulo
> GET /series/top5 - retorna top 5 séries do banco com base em avaliação
> GET /series/lancamentos - retorna as séries mais atuais
> GET /series/{id} busca uma série pelo ID
> GET /series/temporadas/todas - retorna todas as temporadas de uma série
## Como executar o projeto:
1. Clonar o repositório:  
   ```
   git clone https://github.com/seu-usuario/movie-series-api.git
2. Navegue até o diretório do projeto:
   ```
   cd movie-series-api
3. Crie as variáveis de ambiente necessárias no sistema operacional:
   ```
   GEMINI_APIKEY= gere-sua-chave-do-gemini
   DB_USER=user
   DB_PASSWORD=test
   DB_NAME=screenmatchData
   DB_HOST=localhost
   MYSQL_ROOT_PASSWORD=rsua-senha
   OMDB_APIKEY= gere-sua-chave-OMDb

4. Suba o container docker:
   ```
   docker compose up -d
   
5. Executar o projeto:
   ```
   ./mvnw spring-boot:run

6. A API estará disponível em:
   ```
   http://localhost:8080/series

   
   
