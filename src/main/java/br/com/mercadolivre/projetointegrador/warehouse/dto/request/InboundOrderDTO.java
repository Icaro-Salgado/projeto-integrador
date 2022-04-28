package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderDTO {
    private Integer orderNumber;
    private Long warehouseCode;
    private Long sectionCode;
    private List<CreateBatchPayloadDTO> batches;
}
