package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.InboundOrderMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inboundorder")
@RequiredArgsConstructor
public class InboundOrderController {
    private final WarehouseService warehouseService;
    private final InboundOrderMapper inboundOrderMapper = InboundOrderMapper.INSTANCE;

    @PostMapping
    // TODO: EntityModel<?> -> EntityModel<List<SavedBatches>>
    public EntityModel<?> addInboundOrder(@RequestBody InboundOrderDTO dto) {
        InboundOrder inboundOrderToSave = inboundOrderMapper.toModel(dto);
        List<Object> savedBatches = warehouseService.saveBatchInSection(inboundOrderToSave);

        // TODO: Converter o retorno para DTO
        // TODO: Montar o EntityModel
        // TODO: Retornar o created com a URI
        return null;
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
