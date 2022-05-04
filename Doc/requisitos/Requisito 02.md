# Requisito 02

## USER STORY

> **COMO** comprador, **QUERO** adicionar produtos ao carrinho de compras do Marketplace **PARA** comprá-los, se desejar.

## CENÁRIO
> **CENÁRIO 1**: Considerando que produtos estejam registrados em algum warehouse.
> **DESDE** o comprador esteja cadastrado
> **E** que o produto tenha estoque
> **E** que o prazo de validade do produto não seja inferior a 3 semanas
> **QUANDO** o comprador adiciona o produto e a respectiva quantidade ao carrinho
> **ENTÃO**, um produto é adicionado ao carrinho de compras
> **E** o produto deve ter seu estoque atualizado caso a compra seja efetivada.

## VALIDAÇÕES
- Autentique-se como comprador e acesse os terminais.
- Consultar produto
- Adicione um produto ao carrinho do comprador.

## SETUP INICIAL DO WAREHOUSE
Na raíz do projeto execute o seguinte comando.
```shell
./Doc/requisitos/setup_req02.sh
```

### Autenticar o usuário
```shell
curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -d '{
  "email": "john.tre@email.com",
  "password": "123456"
}'
```

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
curl -X GET http://localhost:8080/api/v1/marketplace/fresh-products/orders/{id} -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```

### Efetivando uma compra
```shell
curl -X POST http://localhost:8080/api/v1/customers/marketplace/purchases -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```

### Listando as compras
```shell
curl http://localhost:8080/api/v1/customers/marketplace/purchases -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json"
```

### Alterando status da compra