# Requisito 2

## USER STORY

> **COMO** comprador, **QUERO** adicionar produtos ao carrinho de compras do Marketplace **PARA** comprá-los, se desejar.

## CENÁRIO

> **CENÁRIO 1**: Considerando que produtos estejam registrados em algum warehouse.  
> **DESDE** o _comprador_ esteja cadastrado  
> **E** que <span style="color:green">o produto tenha estoque </span>  
> **E** que <span style="color:green">o prazo de validade do produto não seja inferior a 3 semanas </span>  
> **QUANDO** <span style="color:green">o comprador adiciona o produto e a respectiva quantidade ao carrinho </span>   
> **ENTÃO**, <span style="color:green">um produto é adicionado ao carrinho de compras</span>  
> **E** <span style="color:green">o produto deve ter seu estoque atualizado caso a compra seja efetivada.</span>

## VALIDAÇÕES
- Autentique-se como comprador e acesse os terminais.
- Consultar produto
- Adicione um produto ao carrinho do comprador.

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
./Doc/requisitos/setup.sh
```

### Autenticar o usuário
```shell
curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -d '{
  "email": "marketplace@email.com",
  "password": "123"
}'
```
Nota: Nas próximas requisições, feita a autenticação acima, seu _buyerId_ será 2.
## Preparando o ambiente para testar o requisito 02

### Listando produtos disponíveis
```shell
curl -X GET http://localhost:8080/api/v1/marketplace/ads -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```

### Adicionando um produto no carrinho de compras
```shell
curl -X POST http://localhost:8080/api/v1/marketplace/fresh-products/orders -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json" -d '{
	"products":
	[
		{
			"productId": 1,
			"quantity": 5,
			"unitPrice": 1.99
		},
		{
			"productId": 2,
			"quantity": 10,
			"unitPrice": 7.99
		}
	]
}'
```

### Exibindo carrinho de compras
```shell
curl -X GET http://localhost:8080/api/v1/marketplace/fresh-products/orders/{buyerId} -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```

### Efetivando uma compra
```shell
curl -X POST http://localhost:8080/api/v1/customers/marketplace/purchases -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```

### Listando as compras
```shell
curl http://localhost:8080/api/v1/customers/marketplace/purchases/{buyerId} -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```

### Alterando status da compra
```bash
curl -X PUT http://localhost:8080/api/v1/customers/marketplace/purchases/{buyerId}/{purchaseId} -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```