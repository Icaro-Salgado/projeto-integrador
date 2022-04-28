package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
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
public class CreatedBatchDTO {

  private Long id;

  private Product product;
  private Long section_id;
  private Long seller_id;
  private BigDecimal price;
  private Integer order_number;
  private Integer batchNumber;
  private Integer quantity;
  private LocalDate manufacturing_datetime;
  private LocalDate due_date;
  private LocalDate created_at;

  private List<Map<String, String>> links;

  public static CreatedBatchDTO fromModel(Batch batch) {
    return CreatedBatchDTO.builder()
        .product(batch.getProduct())
        .section_id(batch.getSection_id())
        .seller_id(batch.getSeller_id())
        .price(batch.getPrice())
        .order_number(batch.getOrder_number())
        .batchNumber(batch.getBatchNumber())
        .quantity(batch.getQuantity())
        .manufacturing_datetime(batch.getManufacturing_datetime())
        .due_date(batch.getDue_date())
        .created_at(batch.getCreated_at())
        .build();
  }

  public Batch toModel() {
    return Batch.builder()
        .product(this.product)
        .section_id(this.section_id)
        .seller_id(this.seller_id)
        .price(this.price)
        .order_number(this.order_number)
        .batchNumber(this.batchNumber)
        .quantity(this.quantity)
        .manufacturing_datetime(this.manufacturing_datetime)
        .due_date(this.due_date)
        .created_at(this.created_at)
        .build();
  }
}
