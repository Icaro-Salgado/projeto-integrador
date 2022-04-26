package br.com.mercadolivre.projetointegrador.marketplace.service;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.marketplace.exception.InvalidCategoryException;
import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exception.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            throw new NotFoundException("Produto não encontrado.");
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

    public List<Product> findAll(String querytype) throws InvalidCategoryException, NotFoundException {
        if (querytype == null) {
            return findAll();
        }

        if (!CategoryEnum.contains(querytype)) {
            throw new InvalidCategoryException("Categoria inválida.");
        }

        List<Product> products = productRepository.findAllByCategory(querytype);
        if (products.size() == 0) {
            throw new NotFoundException("Nenhum produto cadastrado");
        }
        return products;
    }

    public List<Product> findAll() {

        return productRepository.findAll();
    }

    public void delete(Long id) throws NotFoundException {
        Product product = findById(id);

        productRepository.delete(product);
    }
}
