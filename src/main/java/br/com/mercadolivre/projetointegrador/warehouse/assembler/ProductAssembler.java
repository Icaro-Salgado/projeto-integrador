package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.warehouse.controller.ProductControllerWarehouse;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.ProductMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.utils.ResponseUtils;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler {

  public ResponseEntity<ProductDTO> toResponse(Product entity, HttpStatus status) {
    ProductDTO dto = ProductMapper.INSTANCE.toDto(entity);

    Links links =
        Links.of(
            linkTo(methodOn(ProductControllerWarehouse.class).getById(entity.getId()))
                .withSelfRel());

    dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));

    return new ResponseEntity<>(dto, status);
  }

  public ResponseEntity<ProductDTO> toResponse(
      Product entity, HttpStatus status, HttpHeaders headers) {
    ProductDTO dto = ProductMapper.INSTANCE.toDto(entity);

    Links links =
        Links.of(
            linkTo(methodOn(ProductControllerWarehouse.class).getById(entity.getId()))
                .withSelfRel());

    dto.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));

    return new ResponseEntity<>(dto, headers, status);
  }

  public ResponseEntity<List<ProductDTO>> toResponse(List<Product> entity, HttpStatus status) {
    List<ProductDTO> dto = ProductMapper.INSTANCE.toDto(entity);

    dto.forEach(
        product -> {
          Links links =
              Links.of(
                  linkTo(methodOn(ProductControllerWarehouse.class).getById(product.getId()))
                      .withSelfRel());

          product.setLinks(List.of(ResponseUtils.parseLinksToMap(links)));
        });

    return new ResponseEntity<>(dto, status);
  }
}
