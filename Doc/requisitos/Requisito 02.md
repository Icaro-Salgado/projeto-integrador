# Requisito 3

## USER STORY

> **COMO** Representante **QUERO** oider consultar um produto em stock no armazém **PARA** saber sua localização num setor e os diferentes lotes onde se encontra.

## CENÁRIO
> **CENÁRIO 1**: um produto do vendedor é registrado.   
> **DESDE** o produto de um Vendedor é registrado   
> **E** que o armazém é válido  
> **E** que o representante pertence ao armazém 
> **E** que o setor é válido    
> **E** que o setor corresponde ao tipo de produto  
> **E** que o setor é dono do lote  
> **E** que o lote possui o produto.    
> **QUANDO** o representante insere o código do produto 
> **ENTÃO** a localização do produto em um setor é exibida  
> **E** o produto tem um número de lote 
> **E** o produto é filtrado (ordenado) por número de lote  
> **E** o produto é filtrado (ordenado) pela quantidade atual do lote (menor para maior)    
> **E** o produto é filtrado (ordenado) por data de validade (mais antigo para o mais novo)

## VALIDAÇÃO

- Autentique-se como representante e acesse os terminais.
- O produto não deve aparecer no setor errado.
- O produto não deve estar vencido ou prestes a expirar(mínimo 3 semanas).
- **OBSERVAÇÃO: substituir as variáveis ${TOKEN} para o resultado de autenticação**

## ENDPOINTS AUXILIARES CRIADOS

Para os endpoints auxiliares, utilzados para a criação os registros iniciais, consulte a documentação do [requisito 01](Requisito%2001.md).

## Diagrama de classes
![image](https://user-images.githubusercontent.com/101267189/166071492-0a17c75d-1c26-4824-a6d6-76c90b151af8.png)


## ENDPOINTS DO CONTRATO CRIADOS

### <span style="color:green">(GET)</span> /api/v1/warehouse/fresh-products/list

#### Definição

Retorna uma lista com todos os produtos e seu respectivo lote dentro de uma _Section_.  

**OBS: Os dados retornados são referente a seção no qual o usuário conectado está associado.**


#### Observações

Para testar você precisa ter

- Um _Product_ cadastrado na base
- Uma _Section_ cadastrada na base e pertencente a um _Warehouse_
- Estar logado com um usuário que tem permissão de escrita na _Warehouse_ e estar associado a uma _Section_
- Ao menos um _Batch_ cadastrado.

#### Para testar

```shell
curl -X GET http://localhost:8080/api/v1/warehouse/fresh-products/list?product={productId} -H "Authorization: Bearer {TOKEN}" -H "Content-Type: application/json" 
```

Os parâmetros aceitos na URI são:

> product={productId} _Obrigatório_     
> sort={Sigla ordenação}

Siglas de ordenação:
- L -> Ordena os resultados por número do lote.
- C -> Ordena os resultas pela quantidade.
- F -> Ordena os resultados pela data de vencimento crescente.


#### Corpo da resposta

```JSON
{
  "warehouse_code": "Long",
  "section_code": "Long",
  "productId": "Long",
  "batchStock": [
    {
      "batchNumber": "Integer",
      "quantity": "Integer",
      "dueDate": "LocalDate"
    }
  ]
}
```

