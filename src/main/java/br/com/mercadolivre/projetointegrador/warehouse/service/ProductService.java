package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.warehouse.model.*;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Service
@AllArgsConstructor
public class ProductService {

  ProductRepository productRepository;
  BatchRepository batchRepository;

  public void createProduct(Product product) throws ProductAlreadyExists {
    Product existingProduct = findByName(product.getName());
    if (existingProduct != null) {
      throw new ProductAlreadyExists("Produto com o nome informado já cadastrado.");
    }
    productRepository.save(product);
  }

  public Product findById(Long id) throws NotFoundException {
    Product product = productRepository.findById(id).orElse(null);

    if (product == null) {
      throw new NotFoundException("Produto " + id + " não encontrado.");
    }

    return product;
  }

  public Product findByName(String name) {
    return productRepository.findByName(name);
  }

  public void updateProduct(Long id, Product updatedProduct) throws NotFoundException {
    Product oldProduct = findById(id);

    oldProduct.setCategory(updatedProduct.getCategory());
    oldProduct.setName(updatedProduct.getName());

    productRepository.save(oldProduct);
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public List<Product> findAllByCategory(CategoryEnum category) {
    return productRepository.findAllByCategory(category);
  }

  public ProductInWarehouses findProductInWarehouse(Long id) throws NotFoundException {
    List<Batch> batches = batchRepository.findAllByProductId(id);

    if (batches.size() <= 0) {
      throw new NotFoundException("Produto " + id + " não encontrado.");
    }

    List<ProductInWarehouse> productsSum = new ArrayList<>();
    Map<Long, Integer> productQtyToSum =
        batches.stream()
            .collect(
                groupingBy(
                    b -> b.getSection().getWarehouse().getId(), summingInt(Batch::getQuantity)));

    for (Map.Entry<Long, Integer> item : productQtyToSum.entrySet()) {
      productsSum.add(
          ProductInWarehouse.builder()
              .warehouseId(item.getKey())
              .productQty(item.getValue())
              .build());
    }

    return ProductInWarehouses.builder().productId(id).warehouses(productsSum).build();
  }

  public void delete(Long id) throws NotFoundException {
    Product product = findById(id);

    productRepository.delete(product);
  }
}
