#  Projeto Jedi Backend

Este é o backend do sistema **Jedi**, uma aplicação construída com **Spring Boot**, **PostgreSQL**, **Cloudinary** e containerizada com **Docker**. Ele fornece uma API robusta para gerenciar dados e arquivos, utilizando uma arquitetura moderna e escalável.

##  Tecnologias Utilizadas

-  Java 17
- Spring Boot
- PostgreSQL
- Docker & Docker Compose
- Caddy (como proxy reverso)

##  Pré-requisitos

Antes de começar, certifique-se de ter instalado:
- [Docker](https://www.docker.com/get-started) e [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/downloads) para clonar o repositório

## Como Rodar o Projeto com Docker

Siga os passos abaixo para executar o projeto em sua máquina usando Docker:

1. **Clone o repositório**:
   ```bash
   git clone git@github.com:cjunior/jedi-back.git
   cd jedi-back
   ```

2. **Crie a pasta local para arquivos**:
   A aplicação usa um volume mapeado para armazenar arquivos localmente. Crie a pasta necessária:
   ```bash
   mkdir -p ~/jedi-arquivos/documentos
   ```
   Essa pasta será mapeada para `/home/app/jedi-arquivos/documentos` no container da aplicação.

3. **Copie o arquivo de ambiente**:
   ```bash
   cp .env.example .env
   ```

4. **Configure as variáveis de ambiente**:
   Edite o arquivo `.env` com suas credenciais e configurações do banco de dados. Exemplo:
   ```bash
   # Banco de Dados
   POSTGRES_USER=usuario
   POSTGRES_DB=db
   POSTGRES_PASSWORD=123
   
   # HOME
   HOME=/root/ # Seu usuário
   
   # URL base da aplicação
   APP_BASE_URL=http://localhost:8080 # Seu dominio
   ```
   
5. **Inicie os containers com Docker Compose**:
   Execute o comando abaixo para construir e iniciar os serviços (Spring Boot, PostgreSQL e Caddy):
   ```bash
   sudo docker compose up -d --build
   ```


6. **Parando os containers**:
   Para parar os serviços, execute:
   ```bash
   docker compose down
   ```

## Configurando um Domínio Personalizado com Caddy

O Caddy atua como proxy reverso e gerencia certificados TLS/SSL automaticamente. Se você deseja usar um domínio personalizado (ex.: `api.jedi.com`) em vez de `localhost`, siga estes passos:

1. **Atualize o Caddyfile**:
   Edite o arquivo `./Caddyfile` para incluir o novo domínio. Exemplo:
   ```caddy
   api.jedi.com {
       reverse_proxy jedi:8080
   }
   ```
   Substitua `api.jedi.com` pelo seu domínio.


2. **Configure o DNS**:
   Certifique-se de que o domínio aponta para o IP do servidor onde o projeto está hospedado (via registros DNS A ou CNAME).


3. **Atualize a variável de ambiente**:
   No arquivo `.env`, ajuste a variável `APP_BASE_URL` para o novo domínio:
   ```bash
    APP_BASE_URL=https://api.banco.ltap.ifce.edu.br
   ```

4. **Reinicie os containers**:
   Após alterar o `Caddyfile` e o `.env`, reinicie os serviços para aplicar as mudanças:
   ```bash
   sudo docker compose down
   sudo docker compose up -d --build
   ```

5. **Verifique o certificado**:
   O Caddy obterá automaticamente um certificado SSL para o novo domínio via Let's Encrypt, desde que o domínio esteja acessível publicamente e o DNS esteja configurado corretamente.

## Estrutura do Docker Compose

O projeto utiliza o `docker-compose.yml` para orquestrar três serviços:
- **jedi**: A aplicação Spring Boot, rodando na porta `8080`.
- **psql**: Banco de dados PostgreSQL, rodando na porta `5432`.
- **caddy_reverse_proxy**: Proxy reverso Caddy, expondo as portas `80` e `443` para acesso externo.

Os serviços compartilham uma rede chamada `jedi-network` e utilizam volumes para persistência de dados (PostgreSQL e Caddy).

##  Estrutura de Diretórios

- `./jedi`: Código-fonte da aplicação Spring Boot.
- `./docker/postgres-data`: Volume para dados do PostgreSQL.
- `./Caddyfile`: Configuração do proxy reverso Caddy.
- `./.env`: Arquivo de variáveis de ambiente.
- `~/jedi-arquivos/documentos`: Pasta local para armazenamento de arquivos da aplicação.


