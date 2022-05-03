package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.warehouse.view.BatchView;
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

  @JsonView({BatchView.BatchAd.class, BatchView.BatchSection.class})
  private Long id;

  @JsonView(BatchView.BatchAd.class)
  private ProductResponseDTO product;

  private Long section_id;
  private UserResponseDTO seller;

  @JsonView({BatchView.BatchAd.class, BatchView.BatchSection.class})
  private BigDecimal price;

  private Integer order_number;

  @JsonView(SectionView.SectionBatches.class)
  private Integer batchNumber;

  @JsonView({BatchView.BatchAd.class, SectionView.SectionBatches.class})
  private Integer quantity;

  private LocalDate manufacturing_datetime;

  @JsonView(BatchView.BatchSection.class)
  private LocalDate dueDate;
  private LocalDate created_at;
  private List<Map<String, String>> links;
}
