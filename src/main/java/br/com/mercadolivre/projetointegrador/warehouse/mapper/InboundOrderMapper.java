package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.repository.AppUserRepository;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.service.ProductService;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InboundOrderMapper {

  @Autowired private ProductService productService;
  @Autowired private AppUserRepository appUserRepository;
  @Autowired private SectionService sectionService;

  public InboundOrder toModel(InboundOrderDTO inboundOrderDTO) {
    List<CreateBatchPayloadDTO> batchPayloadDTOS = inboundOrderDTO.getBatches();
    Section section = sectionService.findSectionById(inboundOrderDTO.getSectionCode());

    List<Batch> batchList =
        batchPayloadDTOS.stream()
            .map(
                dtoBatch -> {
                  Product product = productService.findById(dtoBatch.getProduct_id());
                  AppUser seller =
                      appUserRepository
                          .findById(dtoBatch.getSeller_id())
                          .orElseThrow(
                              () -> new UsernameNotFoundException("Vendedor não encontrado"));

                  return Batch.builder()
                      .product(product)
                      .section(section)
                      .seller(seller)
                      .price(dtoBatch.getPrice())
                      .order_number(inboundOrderDTO.getOrderNumber())
                      .batchNumber(dtoBatch.getBatchNumber())
                      .quantity(dtoBatch.getQuantity())
                      .manufacturing_datetime(dtoBatch.getManufacturing_datetime())
                      .dueDate(dtoBatch.getDue_date())
                      .build();
                })
            .collect(Collectors.toList());

    return InboundOrder.builder()
        .orderNumber(inboundOrderDTO.getOrderNumber())
        .warehouseCode(inboundOrderDTO.getWarehouseCode())
        .sectionCode(inboundOrderDTO.getSectionCode())
        .batches(batchList)
        .build();
  }
}
