package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
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
    public ResponseEntity<List<CreatedBatchDTO>> addInboundOrder(@RequestBody InboundOrderDTO dto) throws NotFoundException {
        InboundOrder inboundOrderToSave = inboundOrderMapper.toModel(dto);

        List<Batch> savedBatches = warehouseService.saveBatchInSection(inboundOrderToSave);

        return assembler.toCreatedResponse(savedBatches);
    }


    @PutMapping
    public EntityModel<?> updateInboundOrder(@RequestBody InboundOrderDTO dto) throws NotFoundException {
        InboundOrder inboundOrderToUpdate = inboundOrderMapper.toModel(dto);

        warehouseService.updateBatchInSection(inboundOrderToUpdate);
        // TODO: Atualizar o InboundOrder
        // TODO: Converter o retorno para DTO
        // TODO: Montar o EntityModel
        // TODO: Retornar created com a URI
        return null;
    }
}
