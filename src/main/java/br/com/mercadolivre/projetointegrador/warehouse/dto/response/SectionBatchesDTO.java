package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.warehouse.view.BatchView;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "batchStock")
@JsonView(SectionView.SectionBatches.class)
public class SectionBatchesDTO {

  private String warehouse_code;
  private Long section_code;
  private Long productId;
  private List<BatchResponseDTO> batchStock;
}
