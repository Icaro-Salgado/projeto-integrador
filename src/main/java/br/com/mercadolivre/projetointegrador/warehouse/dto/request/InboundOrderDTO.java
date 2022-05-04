package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderDTO {

  @NotNull
  private Integer orderNumber;
  @NotNull
  private Long warehouseCode;
  @NotNull
  private Long sectionCode;
  @NotEmpty
  @Valid
  private List<CreateBatchPayloadDTO> batches;
}
