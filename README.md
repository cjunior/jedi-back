# 🧠 Projeto Jedi Backend

Este é o backend do sistema **Jedi**, feito com **Spring Boot**, **PostgreSQL**, **Cloudinary** e containerizado com **Docker**.

---

## 🚀 Tecnologias Utilizadas

- ✅ Java 17
- ✅ Spring Boot
- ✅ PostgreSQL
- ✅ Cloudinary
- ✅ Docker & Docker Compose

---

## ⚙️ Requisitos

- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados
- Java 17 (caso vá rodar localmente sem Docker)

---

## 📦 Rodando o Projeto com Docker

1. Clone o repositório:

```bash
git clone git@github.com:cjunior/jedi-back.git

cd jedi-back
```

2. Copie o arquivo de ambiente:
```bash 
cp .env.example .env
```

3. Configure as variáveis de ambiente no arquivo .env:
```bash

# Cloudinary
CLOUDINARY_CLOUD_NAME=seu_cloud_name
CLOUDINARY_API_KEY=sua_api_key
CLOUDINARY_API_SECRET=sua_api_secret

# Banco de Dados
POSTGRES_USER=admin
POSTGRES_DB=jedi_db
POSTGRES_PASSWORD=123456
```

4. Rode a aplicação com Docker Compose:
```bash
docker compose up -d --build
```
