package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

  ProductRepository productRepository;

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

  public void delete(Long id) throws NotFoundException {
    Product product = findById(id);

    productRepository.delete(product);
  }
}
