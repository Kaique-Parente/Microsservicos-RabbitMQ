# 🔐 Sistema de Autenticação e Envio de E-mails com Microsserviços

![Arquitetura Microsserviços](./banner.png)

Este projeto é um sistema backend desenvolvido com foco em **arquitetura de microsserviços**, **desacoplamento**, **escalabilidade** e **boas práticas de segurança**.

A solução é composta por **microsserviços independentes**, sendo um responsável pela **autenticação e gestão de usuários (Roles)** e outro dedicado ao **envio de e-mails transacionais**, comunicando-se de forma assíncrona por meio de mensageria.

Com **deploy via Docker e Docker Compose**, os microsserviços podem ser iniciados facilmente em qualquer ambiente, simulando um **cenário real de produção**.

---

## 🧩 Arquitetura do Sistema

- Microsserviço de **Autenticação e Usuários**
- Microsserviço de **Envio de E-mails**
- Comunicação **assíncrona via RabbitMQ**
- **Banco de dados isolado por microsserviço**
- Autenticação baseada em **OAuth2 + JWT**, trabalhando com **Roles**
- Envio de e-mails via **SMTP (Gmail)**
- Deploy simplificado com **Docker e Docker Compose**

---

## 🚀 Tecnologias Utilizadas

### **Backend**
- Java
- Spring Boot
- Spring Security
- **OAuth2 + JWT** – Autenticação e autorização
- JPA / Hibernate – Persistência de dados
- **RabbitMQ** – Mensageria assíncrona
- **SMTP (Gmail)** – Envio de e-mails transacionais

### **Banco de Dados**
- MySQL

### **Infraestrutura**
- **Docker**
- **Docker Compose**
  
---

## 🔐 Funcionalidades Principais

- Cadastro e gestão de usuários
- Autenticação segura com **JWT**
- Controle de métodos com **ROLES**
- Publicação de eventos no **RabbitMQ**
- Consumo de eventos para envio de e-mails
- Persistência de dados isolada por microsserviço
- Deploy simplificado via **Docker Compose**
- Tratamento centralizado de exceções
- Simulação de fluxos reais de produção

---

## ⚙️ Como Rodar o Projeto

1. Certifique-se de ter **Docker** e **Docker Compose** instalados.
2. Clone o repositório:
   ```bash
   git clone https://github.com/Kaique-Parente/Microservices-RabbitMQ
   cd Microservices-RabbitMQ
   ```
3. Execute o Compose para buildar e subir todos os containers:
   ```bash
   docker compose up --build
   ```
4. O microsserviço disponível como API será:
   ```bash
   User Service: http://localhost:8081
   ```

---

## :memo: Licença

Este projeto está licenciado sob uma [Licença MIT](LICENSE). 📜
