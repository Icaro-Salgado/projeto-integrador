package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchResponseDTO {

  private Long id;

  private Product product;
  private Long section_id;
  private Long seller_id;
  private BigDecimal price;
  private Integer order_number;

  @JsonView(SectionView.SectionBatches.class)
  private Integer batchNumber;

  @JsonView(SectionView.SectionBatches.class)
  private Integer quantity;

  private LocalDate manufacturing_datetime;

  @JsonView(SectionView.SectionBatches.class)
  private LocalDate dueDate;

  private LocalDate created_at;

  @JsonView(SectionView.SectionBatches.class)
  private List<Map<String, String>> links;

  public static BatchResponseDTO fromModel(Batch batch) {
    return BatchResponseDTO.builder()
        .product(batch.getProduct())
        .section_id(batch.getSection().getId())
        .seller_id(batch.getSeller_id())
        .price(batch.getPrice())
        .order_number(batch.getOrder_number())
        .batchNumber(batch.getBatchNumber())
        .quantity(batch.getQuantity())
        .manufacturing_datetime(batch.getManufacturing_datetime())
        .dueDate(batch.getDueDate())
        .created_at(batch.getCreated_at())
        .build();
  }
}
