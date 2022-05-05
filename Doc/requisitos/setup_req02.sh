echo "========== Registrando o manager ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/auth/register -H "Content-Type: application/json" -s -d '{
  "name": "Warehouse Manager",
  "userName": "wrhmng",
  "email": "warehouse@email.com",
  "password": "123"
}' > /dev/null

echo "========== Registrando o customer ==========\n"
curl -X POST http://localhost:8080/api/v1/marketplace/auth/register -H "Content-Type: application/json" -s -d '{
  "name": "MarketPlace Customer",
  "userName": "mktpcustmr",
  "email": "marketplace@email.com",
  "password": "123"
}' > /dev/null

echo "========== Obtendo o token do manager ==========\n"
output=`curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -s -d '{
 "email": "warehouse@email.com",
 "password": "123"
}'`

echo "========== Obtendo o token do customer ==========\n"
output2=`curl -X POST http://localhost:8080/api/v1/auth -H "Content-Type: application/json" -s -d '{
 "email": "marketplace@email.com",
 "password": "123"
}'`

echo "========== Criando o warehouse ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
  "name": "warehouse",
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

echo "========== Criando o setor ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/section -H "Authorization: Bearer $output " -H "Content-Type: application/json" -s -d '{
  "warehouseId": 1,
  "managerId": 1,
  "minimumTemperature": 12.22,
  "maximumTemperature": 20.00,
  "capacity": 1000,
  "productCategory": "FS"
}' > /dev/null

echo "========== Criando dois produtos ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/fresh-products -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
    "name":  "Alface",
    "category": "FS"
}' > /dev/null
curl -X POST http://localhost:8080/api/v1/warehouse/fresh-products -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
    "name":  "Danoninho",
    "category": "FS"
}' > /dev/null

echo "========== Criando lotes dos produtos 1 e 2 ==========\n"
curl -X POST http://localhost:8080/api/v1/warehouse/inboundorder -H "Authorization: Bearer $output" -H "Content-Type: application/json" -s -d '{
  "orderNumber": 1,
  "warehouseCode": 1,
  "sectionCode": 1,
  "batches": [
    {
        "product_id": 1,
        "seller_id": 1,
        "price": 199.90,
        "batchNumber": 9,
        "quantity": 100,
        "manufacturing_datetime": "2022-05-01",
        "due_date": "2022-05-15"
    },
    {
        "product_id": 1,
        "seller_id": 1,
        "price": 199.90,
        "batchNumber": 10,
        "quantity": 20,
        "manufacturing_datetime": "2022-05-02",
        "due_date": "2022-05-16"
    },
    {
        "product_id": 2,
        "seller_id": 1,
        "price": 399.90,
        "batchNumber": 11,
        "quantity": 150,
        "manufacturing_datetime": "2022-03-01",
        "due_date": "2022-06-01"
    }
  ]
}' > /dev/null

echo "========== Criando o anúncio do produto 01 ==========\n"
curl -X POST http://localhost:8080/api/v1/marketplace/ads -H "Authorization: Bearer $output2" -H "Content-Type: application/json" -d '{
    "batchesId": [9, 10],
    "name": "Alface crocante",
    "quantity": 1,
    "price": 3.90,
    "discount": 0,
    "category": "FS"
}'

echo "========== Criando o anúncio do produto 02 ==========\n"
curl -X POST http://localhost:8080/api/v1/marketplace/ads -H "Authorization: Bearer $output2" -H "Content-Type: application/json" -d '{
    "batchesId": [11],
    "name": "Danoninho",
    "quantity": 1,
    "price": 3.90,
    "discount": 0,
    "category": "FS"
}'