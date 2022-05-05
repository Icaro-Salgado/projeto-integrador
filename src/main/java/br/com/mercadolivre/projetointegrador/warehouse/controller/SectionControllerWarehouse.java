package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.SectionAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredWarehouseRestController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateSectionPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.service.SectionService;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/warehouse/section")
@Tag(name = "[Warehouse] - Section")
public class SectionControllerWarehouse implements SecuredWarehouseRestController {

  private final SectionService sectionService;
  private final SectionAssembler assembler;

  @Operation(
      summary = "RETORNA UMA SEÇÃO",
      description = "Retorna uma seção com id correspondente ao passado na url")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Seção encontrada com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SectionResponseDTO.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Seção inválida",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @JsonView(SectionView.Detail.class)
  @GetMapping("{id}")
  public ResponseEntity<SectionResponseDTO> findById(@PathVariable Long id) {
    Section section = sectionService.findSectionById(id);

    return assembler.toResponse(section, HttpStatus.OK);
  }

  @Operation(summary = "CRIA UMA SEÇÃO", description = "Cria uma seção")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Seção criada com sucesso",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateSectionPayloadDTO.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "A capacidade do setor já foi atingida, ou o setor não corresponde à categoria do"
                    + " produto",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorDTO.class))
            })
      })
  @PostMapping
  public ResponseEntity<SectionResponseDTO> createSection(
      @RequestBody @Valid CreateSectionPayloadDTO payload) {
    Section created = sectionService.createSection(payload);

    return assembler.toResponse(created, HttpStatus.CREATED);
  }
}
