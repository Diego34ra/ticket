# Sistema de Tickets de Atendimentos
## Descrição
Este sistema gerencia os atendimentos de tarefas de uma empresa, permitindo a criação, visualização, e gerenciamento de tickets de atendimento. A aplicação foi desenvolvida com Spring Boot 3.3.2 e Java 17, e integra com um sistema de envio de e-mails utilizando RabbitMQ como producer. O banco de dados utilizado é MySQL e a aplicação usa Lombok para reduzir o boilerplate de código e ModelMapper para mapeamento de objetos.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3.3.2
- Jakarta EE
- Maven
- RabbitMQ (producer para sistema de e-mail)
- MySQL (banco de dados)
- Lombok
- ModelMapper

## Instalação
Clone o repositório:

``` shell
git clone https://github.com/diego34ra/ticket.git
```

Navegue até o diretório do projeto:

``` shell
cd ticket
```

Instale as dependências do projeto com Maven:

``` shell
mvn clean install
```

## Configuração
Configure o banco de dados MySQL no arquivo application.yml:

``` yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ticket?useSSL=false&serverTimezone=UTC
    username: username
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Configure o RabbitMQ com as seguintes propriedades:

``` yaml
spring:
  rabbitmq:
    addresses: {endereço do rabbitmq}
broker:
  queue:
    email:
      name: default.email
```

## Executando a Aplicação
Para rodar o projeto localmente:

- Usando Maven:
  
``` shell
mvn spring-boot:run
```

## Contribuição
1. Faça o fork do projeto
2. Crie uma nova branch (git checkout -b minha-nova-funcionalidade)
3. Faça o commit das suas alterações (git commit -m 'Adiciona nova funcionalidade')
4. Envie para a branch (git push origin minha-nova-funcionalidade)
5. Crie um novo Pull Request
