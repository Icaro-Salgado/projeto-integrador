package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredWarehouseRestController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CategoryListDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/warehouse/fresh-products/categories")
@Tag(name = "[Warehouse] - Categories")
public class CategoryController implements SecuredWarehouseRestController {

  @Operation(summary = "LISTA AS CATEGORIAS CADASTRADAS")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Retorna a lista de categorias cadastradas",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryListDTO.class))
            }),
      })
  @GetMapping
  public ResponseEntity<List<CategoryListDTO>> listCategories() {
    List<CategoryListDTO> categories =
        Arrays.stream(CategoryEnum.values())
            .map(val -> new CategoryListDTO(val.label(), val.name()))
            .collect(Collectors.toList());

    return ResponseEntity.ok(categories);
  }
}
