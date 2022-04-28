package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.marketplace.service.ProductService;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InboundOrderMapper {

    @Autowired
    private ProductService productService;

    public InboundOrder toModel(InboundOrderDTO inboundOrderDTO){
        List<CreateBatchPayloadDTO> batchPayloadDTOS = inboundOrderDTO.getBatches();

        List<Batch> batchList = batchPayloadDTOS.stream().map(dtoBatch -> {
            Product product = productService.findById(dtoBatch.getProduct_id());

            return Batch.builder()
                    .product(product)
                    .section_id(inboundOrderDTO.getSectionCode())
                    .seller_id(dtoBatch.getSeller_id())
                    .price(dtoBatch.getPrice())
                    .order_number(inboundOrderDTO.getOrderNumber())
                    .batchNumber(dtoBatch.getBatch_number())
                    .quantity(dtoBatch.getQuantity())
                    .manufacturing_datetime(dtoBatch.getManufacturing_datetime())
                    .due_date(dtoBatch.getDue_date())
                    .build();
        }).collect(Collectors.toList());

        return InboundOrder.builder()
                .orderNumber(inboundOrderDTO.getOrderNumber())
                .warehouseCode(inboundOrderDTO.getWarehouseCode())
                .sectionCode(inboundOrderDTO.getSectionCode())
                .batches(batchList)
                .build();

    }
}
