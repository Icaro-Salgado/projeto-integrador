echo "========== Registrando o manager ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/auth/register -H "Content-Type: application/json" -s -d '{
  "name": "John Doe",
  "userName": "john",
  "email": "john.doe@email.com",
  "password": "123456"
}' > /dev/null

sleep 1
echo "========== Registrando o customer ==========\n"
curl -X POST http://localhost:8080/api/v1/marketplace/auth/register -H "Content-Type: application/json" -s -d '{
  "name": "John Tre",
  "userName": "jon",
  "email": "john.tre@email.com",
  "password": "123456"
}' > /dev/null

sleep 1
echo "========== Obtendo o token do manager ==========\n"
output=`curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -s -d '{
 "email": "john.doe@email.com",
 "password": "123456"
}'`

sleep 1
echo "========== Obtendo o token do customer ==========\n"
output2=`curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -s -d '{
 "email": "john.tre@email.com",
 "password": "123456"
}'`

sleep 1
echo "========== Criando o warehouse ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
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
}' > /dev/null

sleep 1
echo "========== Criando o setor ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/section -H "Authorization: Bearer $output " -H "Content-Type: application/json" -s -d '{
  "warehouseId": 1,
  "managerId": 1,
  "minimumTemperature": 12.22,
  "maximumTemperature": 20.00,
  "capacity": 1000,
  "productCategory": "FS"
}' > /dev/null

sleep 1
echo "========== Criando dois produtos ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/fresh-products -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
    "name":  "Alface",
    "category": "FS"
}' > /dev/null
curl -X POST http://localhost:8080/api/v1/warehouse/fresh-products -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
    "name":  "danoninho",
    "category": "FS"
}' > /dev/null

sleep 1
echo "========== Criando um lote do produto 1 ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/inboundorder -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
  "orderNumber": 999,
  "warehouseCode": 1,
  "sectionCode": 1,
  "batches":
    [
      {
        "product_id": 1,
        "seller_id": 1,
        "price": 1.90,
        "batch_number": 12,
        "quantity": 1000,
        "manufacturing_datetime": "2022-01-01",
        "due_date": "2022-06-01"
      }
    ]
}' > /dev/null

sleep 1
echo "========== Criando um lote do produto 2 ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/inboundorder -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
  "orderNumber": 999,
  "warehouseCode": 1,
  "sectionCode": 1,
  "batches":
    [
      {
        "product_id": 2,
        "seller_id": 1,
        "price": 7.90,
        "batch_number": 10,
        "quantity": 100,
        "manufacturing_datetime": "2022-01-01",
        "due_date": "2022-06-01"
      }
    ]
}' > /dev/null

sleep 1
echo "========== Criando o anúncio do produto 01 ==========\n"
curl -X POST http://localhost:8080/api/v1/marketplace/ads -H "Authorization: Bearer $output2" -H "Content-Type: application/json" -d '{
  "batchesId": [10, 12],
  "name": "Alface crocante",
  "quantity": 1000,
  "price": 1.99,
  "discount": 5,
  "category": "FS"
}'

sleep 1
echo "========== Criando o anúncio do produto 02 ==========\n"
curl -X POST http://localhost:8080/api/v1/marketplace/ads -H "Authorization: Bearer $output2" -H "Content-Type: application/json" -d '{
  "batchesId": [12],
  "name": "Danoninho",
  "quantity": 100,
  "price": 7.99,
  "discount": 5,
  "category": "FS"
}'