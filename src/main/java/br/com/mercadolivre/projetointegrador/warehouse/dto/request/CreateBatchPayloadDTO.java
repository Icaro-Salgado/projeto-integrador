package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBatchPayloadDTO {

  @NotNull private Long product_id;
  private Long seller_id;
  private BigDecimal price;
  private Integer batchNumber;
  private Integer quantity;
  private LocalDate manufacturing_datetime;
  private LocalDate due_date;
}
