package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBatchPayloadDTO {

    private Long product_id;
    private Long seller_id;
    private BigDecimal price;
    private Integer batch_number;
    private Integer quantity;
    private LocalDate manufacturing_datetime;
    private LocalDate due_date;
}
