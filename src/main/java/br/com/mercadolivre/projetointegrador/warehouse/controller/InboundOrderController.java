package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.assembler.BatchAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CreatedBatchDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.InboundOrderMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inboundorder")
@RequiredArgsConstructor
public class InboundOrderController {

  private final WarehouseService warehouseService;
  private final InboundOrderMapper inboundOrderMapper;
  private final BatchAssembler assembler;

    @PostMapping
    public ResponseEntity<List<CreatedBatchDTO>> addInboundOrder(@RequestBody InboundOrderDTO dto, Authentication authentication) throws NotFoundException {
        AppUser requestUser = (AppUser) authentication.getPrincipal();
        InboundOrder inboundOrderToSave = inboundOrderMapper.toModel(dto);
        inboundOrderToSave.setManagerId(requestUser.getId());

        List<Batch> savedBatches = warehouseService.saveBatchInSection(inboundOrderToSave);

        return assembler.toCreatedResponse(savedBatches);
  }


    @PutMapping
    public ResponseEntity<List<CreatedBatchDTO>> updateInboundOrder(@RequestBody InboundOrderDTO dto, Authentication authentication) throws NotFoundException {
        InboundOrder inboundOrderToUpdate = inboundOrderMapper.toModel(dto);
        AppUser requestUser = (AppUser) authentication.getPrincipal();
        inboundOrderToUpdate.setManagerId(requestUser.getId());
        List<Batch> updatedBatches = warehouseService.updateBatchInSection(inboundOrderToUpdate);

        return assembler.toCreatedResponse(updatedBatches);
  }
}
