package br.com.mercadolivre.projetointegrador.marketplace.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    ProductRepository productRepository;

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Product findById(Long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new NotFoundException("Produto não encontrado.");
        }

        return product;
    }

    public void updateProduct(Long id, Product updatedProduct) throws NotFoundException {
        Product oldProduct = findById(id);

        oldProduct.setCategory(updatedProduct.getCategory());
        oldProduct.setPrice(updatedProduct.getPrice());
        oldProduct.setName(updatedProduct.getName());

        productRepository.save(oldProduct);
    }
}
