package br.com.mercadolivre.projetointegrador.marketplace.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dto.CreateOrUpdateProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.marketplace.exception.InvalidCategoryException;
import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exception.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

    ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(
            @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
            UriComponentsBuilder uriBuilder
    ) throws InvalidCategoryException, ProductAlreadyExists {
        Product product = createOrUpdateProductDTO.mountProduct();
        productService.createProduct(product);

        URI uri = uriBuilder.path("/api/v1/fresh-products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
            UriComponentsBuilder uriBuilder
  ) throws NotFoundException, InvalidCategoryException {

        Product product = createOrUpdateProductDTO.mountProduct();
        productService.updateProduct(id, product);

        URI uri = uriBuilder.path("/api/v1/fresh-products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.noContent().location(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) throws NotFoundException {
        Product product = productService.findById(id);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> ListProducts (
            @RequestParam(required = false) String querytype) throws InvalidCategoryException, NotFoundException {
        List<Product> products = productService.findAll(querytype);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() throws NotFoundException {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            throw new NotFoundException("Nenhum produto cadastrado.");
        }
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> exclude(@PathVariable Long id) throws NotFoundException {
       productService.delete(id);
       return ResponseEntity.noContent().build();
    }
}
