package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.SectionAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.WarehouseAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.docs.config.SecuredWarehouseRestController;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionBatchesDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.WarehouseResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.WarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/warehouse")
@Tag(name = "[Warehouse] - Warehouse")
public class WarehouseControllerWarehouse implements SecuredWarehouseRestController {

    private final WarehouseService warehouseService;
    private final WarehouseAssembler assembler;
    private final SectionAssembler sectionAssembler;

    @Operation(summary = "CRIA UM NOVO ARMAZÉM", description = "Cria um novo armazém/depósito ")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Armazém criado com sucesso",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CreateWarehousePayloadDTO.class))
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados invalidos!",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorDTO.class))
                            })
            })
    @PostMapping
    public ResponseEntity<WarehouseResponseDTO> createWarehouse(
            @RequestBody @Valid CreateWarehousePayloadDTO payloadDTO) {
        Warehouse created =
                warehouseService.createWarehouse(WarehouseMapper.INSTANCE.createDtoToModel(payloadDTO));

        HttpHeaders headers = new HttpHeaders();
        headers.add(
                "Location",
                linkTo(methodOn(WarehouseControllerWarehouse.class).findById(created.getId()))
                        .withSelfRel()
                        .toString());

        return assembler.toResponse(created, HttpStatus.CREATED, headers);
    }


    @Operation(summary = "RETORNA UM ARMAZÉM CADASTRADO", description = "Retorna um armazém/depósito já cadastrado na base")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retorno ok",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = WarehouseResponseDTO.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Armazém não encontrado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorDTO.class))
                            })
            })
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> findById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.findWarehouse(id);

        return assembler.toResponse(warehouse, HttpStatus.OK, null);
    }

    @Operation(summary = "RETORNA OS LOTES CADASTRADOS DO PRODUTO", description = "Retorna os lotes do produto que estão armazenados na base")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retorno ok",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SectionBatchesDTO.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Armazém, sessão ou produto não encontrado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorDTO.class))
                            })
            })
    @GetMapping("/fresh-products/list")
    @JsonView(SectionView.SectionWithBatches.class)
    public ResponseEntity<SectionBatchesDTO> listStockProducts(
            @RequestParam(required = false) Long product,
            @RequestParam(required = false, defaultValue = "L") SortTypeEnum sort,
            Authentication authentication)
            throws NotFoundException {
        AppUser requestUser = (AppUser) authentication.getPrincipal();
        Long managerId = requestUser.getId();

        if (product == null) {
            throw new IllegalArgumentException("Informe o ID do produto");
        }

        List<Batch> batchProducts =
                warehouseService.findProductOnManagerSection(managerId, product, sort);

        return sectionAssembler.toSectionBatchesResponse(batchProducts, product, HttpStatus.OK);
    }
}
