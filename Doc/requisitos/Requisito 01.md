# Requisito 1

## USER STORY
> **COMO** Representante do armazém de distribuição, **QUERO** inserir um lote de produtos no armazém de distribuição **PARA** registrar a existência de estoque.  

## CENÁRIO
> **CENÁRIO 1**: O produto de um _Vendedor_ é registrado.  
> **DESDE** que o produto de um _Vendedor_ é registrado  
> **E** que o <span style="color:green">armazém é valido</span>   
> **E** que o <span style="color:green">representante pertence ao armazém</span>  
> **E** que o <span style="color:green">setor é válido</span>   
> **E** que o <span style="color:green">setor corresponde ao tipo de produto</span>  
> **E** que o <span style="color:green">setor tenha espaço disponível</span>  
> **QUANDO** o <span style="color:green">representante entra no setor</span>  
> **ENTÃO** o <span style="color:green">registro de compra é criado</span>  
> **E** o <span style="color:green">lote é atribuído a um setor</span>  
> **E** o <span style="color:green">representante é associado ao registro de estoque</span>  


## VALIDAÇÃO
> - Autentique-se como representante e acesse os terminais.
> - Registre o lote no setor correspondente.
> - Verifique se o setor de warehouse está sendo registrado corretamente.

## ENDPOINTS DO CONTRATO CRIADOS

### <span style="color:green">(POST)</span> /api/v1/inboundorder 

#### Para testar

```shell
curl -X POST http://localhost:8080/api/v1/inboundorder -H "Content-Type: application/json" -d '{
{
  "orderNumber": 999,
  "warehouseCode": 1,
  "sectionCode": 1,    
  "batches": 
    [        
      {            
        "product_id": 2,            
        "seller_id": 1,            
        "price": 199.90,            
        "batch_number": 12,            
        "quantity": 100,            
        "manufacturing_datetime": "2022-01-01",            
        "due_date": "2022-06-01"        
      }    
    ]
}",
```
#### Observações 
Para testar você precisa ter
- Um _Product_ cadastrado na base
- Uma _Section_ cadastrada na base e pertencente a um _Warehouse_
- Estar logado com um usuário que tem permissão de escrita na _Warehouse_

### <span style="color:blue">(PUT)</span> /api/v1/inboundorder

#### Para testar

```shell
curl -X POST http://localhost:8080/api/v1/inboundorder -H "Content-Type: application/json" -d '{
{
  "orderNumber": 999,
  "warehouseCode": 1,
  "sectionCode": 1,    
  "batches": 
    [        
      {            
        "product_id": 2,            
        "seller_id": 1,            
        "price": 299.90,            
        "batch_number": 12,            
        "quantity": 100,            
        "manufacturing_datetime": "2022-01-01",            
        "due_date": "2022-06-01"        
      }    
    ]
}",
```

#### Observações
Para testar você precisa ter
- Ter a batch já cadastrada na base 
- Estar logado com um usuário que tem permissão de escrita na _Warehouse_
