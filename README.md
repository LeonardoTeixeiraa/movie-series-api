# 🎬 Movie Series API
API REST desenvolvida com Spring Boot para integração com a API OMDb, permitindo a busca de séries, persistência dos dados em banco MySQL e consultas detalhadas sobre séries e episódios.

O projeto utiliza:
- MySQL containerizado com Docker
- Migrations e versionamento de banco de dados com Flyway
- Arquitetura em camadas (Controller, Service e Repository)
- Integração com IA (Gemini) para realizar traduções de sinopses
- Boas práticas de configuração e gerenciamento de ambiente

## Tecnologias

- Java 21+

- Spring Boot

- Spring Web

- Spring Data JPA

- MySQL

- Maven

- OMDb API
  
- Docker

- Flyway

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

> GET /series/buscar-por-titulo?titulo= - buscar uma serie no banco pelo parâmetro titulo

> GET /series/top5 - retorna top 5 séries do banco com base em avaliação

> GET /series/lancamentos - retorna as séries mais atuais

> GET /series/{id} - busca uma série pelo ID

> GET /series/{id}/temporadas/todas - retorna todas as temporadas cadastradas no banco de uma série

> GET /series/categoria/{genero} - retorna séries por gênero

> GET /series/{id}/temporada/top - retorna as melhores temporadas de uma série

> GET /series/{id}/temporadas/{numero} - retorna uma temporada específica

## Como executar o projeto:
1. Clonar o repositório:  
   ```
   git clone https://github.com/seu-usuario/movie-series-api.git
2. Navegue até o diretório do projeto:
   ```
   cd movie-series-api/
3. Crie as variáveis de ambiente necessárias no sistema operacional:
   ```
   GEMINI_APIKEY= gere-sua-chave-do-gemini (necessária para fazer a tradução da sinopse das séries)
   DB_USER=user
   DB_PASSWORD=sua-senha
   DB_NAME=nome-do-banco
   DB_HOST=localhost
   MYSQL_ROOT_PASSWORD=sua-senha
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
