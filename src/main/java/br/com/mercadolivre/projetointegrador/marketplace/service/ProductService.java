package br.com.mercadolivre.projetointegrador.marketplace.service;

import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    ProductRepository productRepository;

    public void createProduct(Product product) {
        productRepository.save(product);
    }

}
