# Task API DevOps

API REST desenvolvida em **Java 21 + Spring Boot**, criada como laboratório prático para estudar desenvolvimento de APIs e, principalmente, aplicar conceitos de **DevOps, CI/CD, Docker e infraestrutura**.

O projeto utiliza PostgreSQL como banco de dados e possui testes automatizados executados pelo GitHub Actions.

> 🎯 **Objetivo:** utilizar uma aplicação Java/Spring Boot como base para praticar ferramentas e conceitos de DevOps.

---

## 🚀 Tecnologias

### Aplicação

* Java 21
* Spring Boot
* Maven
* Spring Web
* Spring Data JPA
* Hibernate
* Bean Validation

### Banco de dados

* PostgreSQL 16

### DevOps

* Git
* GitHub
* GitHub Actions
* Docker
* Docker Compose
* CI
* Branch Protection
* Pull Requests

### Próximas etapas

* Kubernetes
* Kind
* Terraform
* AWS
* Container Registry
* Observabilidade
* Segurança de containers

---

## 🏗️ Arquitetura

A aplicação segue uma estrutura simples baseada em camadas:

```text
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
PostgreSQL
```

### Estrutura do projeto

```text
taskapi/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/taskapi/
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── TaskController.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   └── TaskService.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   └── Task.java
│   │   │       │
│   │   │       ├── dto/
│   │   │       │   └── TaskRequest.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   └── TaskRepository.java
│   │   │       │
│   │   │       └── exception/
│   │   │           ├── TaskNotFoundException.java
│   │   │           └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── .gitignore
├── pom.xml
└── README.md
```

---

# 📋 Funcionalidades

A API possui operações básicas para gerenciamento de tarefas.

| Método | Endpoint      | Descrição              |
| ------ | ------------- | ---------------------- |
| GET    | `/tasks`      | Lista todas as tarefas |
| GET    | `/tasks/{id}` | Busca uma tarefa       |
| POST   | `/tasks`      | Cria uma tarefa        |
| PUT    | `/tasks/{id}` | Atualiza uma tarefa    |
| DELETE | `/tasks/{id}` | Remove uma tarefa      |

---

# 📦 Modelo de Task

Uma tarefa possui:

```json
{
  "id": 1,
  "title": "Estudar Kubernetes",
  "description": "Aprender Pods e Deployments",
  "completed": false
}
```

Para criar ou atualizar uma tarefa:

```json
{
  "title": "Estudar Kubernetes",
  "description": "Aprender Pods e Deployments"
}
```

O campo `completed` começa como `false`.

---

# ▶️ Executando localmente

## Pré-requisitos

Instale:

* Java 21
* Maven
* Docker
* Git

Verifique:

```bash
java -version
```

```bash
mvn -version
```

```bash
docker --version
```

```bash
git --version
```

---

# 🗄️ PostgreSQL

O projeto utiliza PostgreSQL.

Para iniciar somente o banco:

```bash
docker run -d \
  --name task-postgres \
  -e POSTGRES_DB=taskdb \
  -e POSTGRES_USER=taskuser \
  -e POSTGRES_PASSWORD=taskpass \
  -p 5432:5432 \
  postgres:16
```

Verifique:

```bash
docker ps
```

---

# ⚙️ Configuração

O projeto utiliza variáveis de ambiente para configurar a conexão com o banco.

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/taskdb}
spring.datasource.username=${DB_USERNAME:taskuser}
spring.datasource.password=${DB_PASSWORD:taskpass}
```

Caso as variáveis não estejam definidas, os valores padrão serão utilizados para desenvolvimento local.

---

# 🧪 Testes

Os testes podem ser executados através do Maven:

```bash
mvn clean test
```

Para gerar o JAR:

```bash
mvn package
```

O artefato será criado em:

```text
target/
```

---

# 🐳 Docker

O projeto possui um `Dockerfile` baseado em Java 21.

Build da aplicação:

```bash
mvn clean package
```

Build da imagem:

```bash
docker build -t taskapi:1.0 .
```

Verificar a imagem:

```bash
docker images
```

---

# 🐳 Docker Compose

Também existe uma configuração para executar a aplicação junto com PostgreSQL:

```bash
docker compose up --build
```

A arquitetura do Compose é:

```text
┌──────────────────────┐
│      taskapi         │
│   Spring Boot        │
│      :8080           │
└──────────┬───────────┘
           │
           │ PostgreSQL
           ▼
┌──────────────────────┐
│     PostgreSQL       │
│       :5432          │
└──────────────────────┘
```

Dentro do Docker Compose, a aplicação utiliza:

```text
postgres:5432
```

como endereço do banco.

---

# 🔄 CI — GitHub Actions

O projeto possui um pipeline de integração contínua através do GitHub Actions.

Arquivo:

```text
.github/workflows/ci.yml
```

O pipeline executa:

```text
Push / Pull Request
        │
        ▼
Checkout
        │
        ▼
Java 21
        │
        ▼
PostgreSQL
        │
        ▼
Maven Test
        │
        ▼
Build
```

O CI executa os testes automaticamente antes que alterações sejam integradas à `main`.

---

# 🔐 Proteção da branch

A branch `main` possui regras de proteção.

Atualmente:

* Pull Request obrigatório
* CI obrigatório
* Branch atualizada antes do merge
* Force push bloqueado

Fluxo utilizado:

```text
feature/*
    │
    ▼
Pull Request
    │
    ▼
GitHub Actions
    │
    ├── Testes
    └── Build
    │
    ▼
    ✓ CI
    │
    ▼
Merge
    │
    ▼
main
```

---

# 🌱 Fluxo Git

Para uma nova alteração:

```bash
git checkout -b feature/minha-alteracao
```

Depois:

```bash
git add .
```

```bash
git commit -m "feat: minha alteração"
```

```bash
git push -u origin feature/minha-alteracao
```

Em seguida, abrir um Pull Request para `main`.

---

# 📚 O que estou praticando

Este projeto está sendo utilizado para praticar:

### Java

* Orientação a objetos
* Classes
* Métodos
* Interfaces
* Exceptions
* Spring Boot
* REST APIs
* JPA
* Validação
* Testes

### DevOps

* Git e GitHub
* Branches
* Pull Requests
* CI
* GitHub Actions
* Maven
* Docker
* Docker Compose
* Variáveis de ambiente
* Containers
* Automação de testes

---

# 🗺️ Roadmap

O projeto será evoluído gradualmente como um laboratório de DevOps.

```text
[x] Java 21
[x] Spring Boot
[x] REST API
[x] PostgreSQL
[x] JPA / Hibernate
[x] Validação
[x] Testes
[x] Maven
[x] Git / GitHub
[x] Dockerfile
[x] Docker Compose
[x] GitHub Actions
[x] Branch Protection

[ ] Build automático da imagem Docker
[ ] Container Registry
[ ] Kubernetes
[ ] Kind
[ ] Deployment
[ ] Service
[ ] ConfigMap
[ ] Secrets
[ ] Health Checks / Probes
[ ] Terraform
[ ] AWS
[ ] Observabilidade
[ ] Segurança de containers
```

---

# 🎯 Objetivo profissional

Este projeto não tem como objetivo ser uma aplicação comercial.

Ele funciona como um **laboratório prático de DevOps**, utilizando Java/Spring Boot como aplicação base para experimentar processos de:

```text
Development
     ↓
Version Control
     ↓
Testing
     ↓
CI
     ↓
Containers
     ↓
Kubernetes
     ↓
Infrastructure as Code
     ↓
Cloud
     ↓
Observability
     ↓
Security
```

A ideia é evoluir a infraestrutura e o processo de entrega continuamente, mantendo a aplicação simples para que o foco permaneça nos conceitos de **DevOps, Cloud e infraestrutura**.
