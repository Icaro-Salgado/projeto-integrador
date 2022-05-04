package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.ProductAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredWarehouseRestController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateOrUpdateProductDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.ProductInWarehousesAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductInWarehouseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.InvalidCategoryException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.ProductInWarehouses;
import br.com.mercadolivre.projetointegrador.warehouse.service.ProductService;
import br.com.mercadolivre.projetointegrador.warehouse.view.ProductView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/warehouse/fresh-products")
@Tag(name = "[Warehouse] - Product")
public class ProductControllerWarehouse implements SecuredWarehouseRestController {

  private final ProductService productService;
  private final ProductAssembler productAssembler;
  private final ProductInWarehousesAssembler productInWarehousesAssembler;

  @Operation(
      summary = "CRIA UM NOVO PRODUTO",
      description = "Cria um novo produto através dos parametros:  name | category ")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto criado com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Product.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Dados invalidos!",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Produto já existe",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @PostMapping
  public ResponseEntity<ProductDTO> createProduct(
      @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
      UriComponentsBuilder uriBuilder)
      throws InvalidCategoryException, ProductAlreadyExists {
    Product product = createOrUpdateProductDTO.mountProduct();
    productService.createProduct(product);

    URI uri =
        uriBuilder
            .path("/api/v1/warehouse/fresh-products/{id}")
            .buildAndExpand(product.getId())
            .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", uri.toString());

    return productAssembler.toResponse(product, HttpStatus.CREATED, headers);
  }

  @Operation(
      summary = "ATUALIZA UM PRODUTO",
      description = "Atualiza um produto através de um ID válido")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateOrUpdateProductDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Produto não encontrado ou Categoria Inválida",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateProduct(
      @PathVariable Long id,
      @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO,
      UriComponentsBuilder uriBuilder)
      throws NotFoundException, InvalidCategoryException {

    Product product = createOrUpdateProductDTO.mountProduct();
    productService.updateProduct(id, product);

    URI uri =
        uriBuilder
            .path("/api/v1/warehouse/fresh-products/{id}")
            .buildAndExpand(product.getId())
            .toUri();

    return ResponseEntity.noContent().location(uri).build();
  }

  @Operation(
      summary = "OBTEM UM PRODUTO PELO ID",
      description = "Obtem um produto informando um ID válido")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Product.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @GetMapping("/{id}")
  @JsonView(ProductView.Detail.class)
  public ResponseEntity<ProductDTO> getById(@PathVariable Long id) throws NotFoundException {
    Product product = productService.findById(id);

    return productAssembler.toResponse(product, HttpStatus.OK);
  }

  @Operation(summary = "OBTEM TODOS OS PRODUTOS", description = "Obtem uma lista de produtos")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produtos encontrados",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Product.class))
            })
      })
  @GetMapping
  @JsonView(ProductView.List.class)
  public ResponseEntity<List<ProductDTO>> getAll(
      @RequestParam(required = false) CategoryEnum category) {
    List<Product> products =
        category != null ? productService.findAllByCategory(category) : productService.findAll();

    HttpStatus status = products.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;

    return productAssembler.toResponse(products, status);
  }


@Operation(summary = "OBTEM TODAS AS LOCALIDADES DE UM PRODUTO", description = "Obtem todas as localidades de um produto informado")
@ApiResponses(
    value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductInWarehouses.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDTO.class))
                    })
    })
  @GetMapping("/location/{id}")
  public ResponseEntity<ProductInWarehouseDTO> productInWarehouse(@PathVariable Long id) throws NotFoundException {
     return productInWarehousesAssembler.toResponse(productService.findProductInWarehouse(id));
  }

  @Operation(
      summary = "DELETA UM PRODUTO",
      description = "Deleta um produto através de um ID válido")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> exclude(@PathVariable Long id) throws NotFoundException {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
