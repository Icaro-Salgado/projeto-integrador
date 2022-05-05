# Requisito 5

## USER STORY

> **COMO** Representante **QUERO** poder consultar os produtos em estoque que estão prestes a expirar no almoxarifado, a fim de aplicar alguma ação comercial com eles.
## CENÁRIO

> **CENÁRIO 1**: um produto do vendedor é registrado
> **DESDE** que o produto de um Vendedor é registrado  
> **E** que o armazém é válido  
> **E** que o representante pertence ao armazém  
> **QUANDO** o representante insere o número de dias  
> **ENTÃO**, uma lista de produtos é exibida com uma data de validade entre a data atual e a data futura (data atual + dias inseridos)
> **E** que o produto tem um número de lote  
> **E** que o produto é filtrado por número de lote  
> **E** que o produto é filtrado por data de validade  
> **E** que o produto seja filtrado de acordo com a categoria dos produtos (frescos, congelados, refrigerados)
## VALIDAÇÃO

- Autentique-se como representante e acesse os terminais
- O produto não deve aparecer no setor errado
- O produto deve aparecer em lotes diferentes
- A data de validade deve estar dentro do intervalo inserido

## SETUP INICIAL
Na raíz do projeto, primeiro crie e inicie os containers com:
```shell  
# Linux/macOS
docker-compose -f docker/docker-compose.yaml up -d
```

Na sequência, rode o projeto na sua IDE ou, se preferir, rode o projeto localmente executando os comandos:
```bash  
# instala as dependências do maven  
mvn install
# realiza o build do projeto  
mvn package  
# inicia a aplicação  
java -jar ./target/projeto-integrador-X.X.X-SNAPSHOT.jar  
```  
## Preparando o ambiente para testar o requisito 05
### Criar usuário
```shell  
curl -X POST http://localhost:8080/api/v1/warehouse/auth/register -H "Content-Type: application/json" -d '{
	"name": "John Doe",
	"userName": "john",
	"email": "john.doe@email.com",
	"password": "123456" 
}'
```

### Autenticar usuário
```shell  
curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -d '{
	"email": "john.doe@email.com",
	"password": "123456"
}'  
```  

### Criar warehouse
```shell
curl -X POST http://localhost:8080/api/v1/warehouse -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json" -d '{
	"name": "warehouse 1",
	"location": { 
		"country": "Brazil",
		"state": "SP",
		"city": "Osasco",
		"neighborhood": "Bomfim",
		"street": "Av. das Nações Unidas",
		"number": 3003, 
		"zipcode": 6233200
	}
}'
```

### Criar section
```shell
curl -X POST http://localhost:8080/api/v1/warehouse/section -H "Authorization: Bearer {TOKEN} " -H "Content-Type: application/json" -d '{
	"warehouseId": 1,
	"managerId": 1,
	"minimumTemperature": 12.22,
	"maximumTemperature": 20.00,
	"capacity": 1000,
	"productCategory": "FS"
}'
```

### Criar product
```shell
curl -X POST http://localhost:8080/api/v1/warehouse/fresh-products -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json" -d '{
	"name": "Alface",
	"category": "FS" 
}'
```
### Criar inboundOrder
```shell
curl -X POST http://localhost:8080/api/v1/warehouse/inboundorder -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json" -d '{
	"orderNumber": 999,
	"warehouseCode": 1,
	"sectionCode": 1,
	"batches": 
	  [
	    {  
		  "product_id": 1,  
		  "seller_id": 1,  
		  "price": 199.90,  
		  "batchNumber": 12,  
		  "quantity": 1,  
		  "manufacturing_datetime": "2022-01-01",  
		  "due_date": "2022-05-10"  
		}  
	  ]
  }'
```
## ENDPOINTS AUXILIARES CRIADOS

### <span style="color:green">(GET)</span> /api/v1/warehouse/fresh-products/duedate?numb_days={30}&section_id={1}
Nesse endpoint é possível consultar lotes a vencer de um determinado setor.

 ```shell  
curl -X GET http://localhost:8080/api/v1/warehouse/fresh-products/duedate?numb_days=30&section_id=1 -H "Authorization: Bearer {TOKEN}"
``` 

#### Observações
- altere o valor de numb_days do endpoint acima, para mudar a data limite de vencimento dos produtos a contar da data de hoje.
- altere o valor de section_id do endpoint acima, para mudar a section procurada.

### <span style="color:green">(GET)</span> /api/v1/warehouse/fresh-products/duedate-batches?numb_days={30}&category={FF}&order={ASC}

Nesse endpoint é possível consultar lotes a vencer de uma determinada categoria e ordená-los por data do vencimento.

 ```shell  
curl -X GET http://localhost:8080/api/v1/warehouse/fresh-products/duedate-batches?numb_days=30&category=FF&order=ASC -H "Authorization: Bearer {TOKEN}"
``` 

#### Observações
- altere o valor de numb_days do endpoint acima, para mudar a data limite de vencimento dos produtos a contar da data de hoje.
- altere o valor de category do endpoint acima, para mudar  mudar a categoria pesquisada. **Opções possíveis** (FS fresco, RF refrigerado, FF congelado)