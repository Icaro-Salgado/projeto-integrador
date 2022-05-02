package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.view.BatchView;
import com.fasterxml.jackson.annotation.JsonView;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
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

    @JsonView(BatchView.BatchAd.class)
    private Long id;
    @JsonView(BatchView.BatchAd.class)
    private ProductResponseDTO product;
    private Long section_id;
    private UserResponseDTO seller;
    @JsonView(BatchView.BatchAd.class)
    private BigDecimal price;
    private Integer order_number;
    private Integer batchNumber;
    @JsonView(BatchView.BatchAd.class)
    private Integer quantity;
    private LocalDate manufacturing_datetime;
    private LocalDate dueDate;
    private LocalDate created_at;
    private List<Map<String, String>> links;

}
