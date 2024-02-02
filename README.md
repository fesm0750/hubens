# Hubens

Hubens é uma plataforma de vídeos. Tem por principal objetivo a utilização do paradigma de programação reativa utilizando Spring Webflux, de forma a prover uma solução eficiente ao utilizar ao trabalhar com fluxos de dados assíncronos e não bloqueantes.

Em desenvolvimento como trabalho de curso da pós-graduação em Arquitetura e Desenvolvimento Java na FIAP.

Requsitos: Java 17, Docker e Docker Composer.

## Executado a aplicação

Para rodar a aplicação utilize o seguinte comando:

```shell script
./mvnw spring-boot:run
```

## Configuração do banco de dados

A aplicação está configurada para acessar instâncias do banco de dados Mongo DB rodando localmente. Na pasta mongo, dentro da estrutura de arquivos do projeto se encontra o arquivo `docker-compose.yml` que configura uma instância do banco de dados mongo e também da interface de administração web Express. Para subir as instâncias basta executar o comando abaixo com o terminal apontando para a pasta onde o arquivo se encontra presente.

```shell script
docker-compose up -d
```

O arquivo `compose` também define o armazenamnto local dos arquivos do banco de dados, o qual serão salvos na pasta mongo/data_db no diretório do projeto. Pode ser necessário alterar a linha de definição de volume no arquivo `docker-compose.yml` e remover a sequência `:Z` ao final. Tal opção teve de ser adicionada para ajustar as permissões dos arquivos salvos em que o host implementa SELinux.

```
    volumes:
      - ./data_db/:/data/db/:Z
```

## Testes

Para rodar os testes basta executar o seguinte comando:

```shell script
./mvnw test
```

Ou então o comando abaixo para executar os testes e gerar o relatório de cobertura do JaCoCo.

```shell script
./mvnw jacoco:prepare-agent test install jacoco:report
```

O relatório de cobertura pode ser acessado a partir de `/target/site/jacoco/index.html`.

Para os testes de integração é utilizada a tecnologia testcontainers, que permite a execução de instâncias leves e descartáveis do banco de dados em containers Docker durante a realização dos testes.

## Referencias Relacionadas

Para mais informações sobre algumas das tecnologias utilizadas, considere as seguintes referências:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/#build-image)
* [Official Testcontainers website](https://testcontainers.com/)