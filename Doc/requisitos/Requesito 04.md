# Requisito 4

## USER STORY

> **COMO** Representante, **QUERO** poder consultar um produto em todos os armazéns **PARA** saber o estoque de cada armazém do referido produto.

## CENÁRIO

> **CENÁRIO 1**: um produto do vendedor é registrado.  
> **DESDE** o produto de um vendedor é registrado 
> </br>**E** que <span style="color:green">o armazém é válido </span>
> </br>**QUANDO** <span style="color:green">o representante insere o código do produto </span>   
> **ENTÃO**, <span style="color:green">a quantidade do produto é exibida em cada armazém</span>  

## VALIDAÇÕES
- Autentique-se como representante e acesse os terminais.

## SETUP INICIAL DO WAREHOUSE
Na raíz do projeto, primeiro crie e inicie os containers com
```shell
# Linux
docker-compose -f docker/docker-compose.yaml up -d
```
```shell
# macOS
docker compose -f docker/docker-compose.yaml up -d
```
Na sequencia, rode o projeto na sua IDE ou, se preferir, rode o projeto localmente executando os comandos:
```bash
# instala as dependências do maven
mvn install

# realiza o build do projeto
mvn package

# inicia a aplicação
java -jar ./target/projeto-integrador-0.0.1-SNAPSHOT.jar

```
Depois, execute o seguinte comando para preparar o banco:
```shell
./Doc/requisitos/setup_req04.sh
```

### Autenticar o usuário
```shell
curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -d '{
  "email": "john.doe@email.com",
  "password": "123456"
}'
```
## Preparando o ambiente para testar o requisito 04

### Listando a localidade(Armazén) e o estoque total de um produto
```shell
curl -X 'GET' \
  'http://localhost:8080/api/v1/warehouse/fresh-products/location/{productID}' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer {TOKEN}'
```