package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
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
}
