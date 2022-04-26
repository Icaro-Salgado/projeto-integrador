package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.BatchAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CreatedBatchDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.InboundOrderMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inboundorder")
@RequiredArgsConstructor
public class InboundOrderController {
    private final WarehouseService warehouseService;
    private final InboundOrderMapper inboundOrderMapper = InboundOrderMapper.INSTANCE;
    private final BatchAssembler assembler;

    @PostMapping
    public ResponseEntity<List<CreatedBatchDTO>> addInboundOrder(@RequestBody InboundOrderDTO dto) {
        InboundOrder inboundOrderToSave = inboundOrderMapper.toModel(dto);

        // TODO: Quando o serivce retornar o tipo correto, fazer a conversão
        List<Object> savedBatches = warehouseService.saveBatchInSection(inboundOrderToSave);
        List<Batch> savedBatchesTemporary = new ArrayList<>();

        return assembler.toCreatedResponse(savedBatchesTemporary);
    }


    @PutMapping
    // TODO: EntityModel<?> -> EntityModel<SavedBatch>
    public EntityModel<?> updateInboundOrder(@RequestBody InboundOrderDTO dto, @PathVariable String id) {
        InboundOrder inboundOrderToUpdate = inboundOrderMapper.toModel(dto);
        // TODO: Atualizar o InboundOrder
        // TODO: Converter o retorno para DTO
        // TODO: Montar o EntityModel
        // TODO: Retornar created com a URI
        return null;
    }
}
