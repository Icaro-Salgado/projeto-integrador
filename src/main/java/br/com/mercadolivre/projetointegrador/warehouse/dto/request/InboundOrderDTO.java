package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderDTO {
    private Integer orderNumber;
    private Long warehouseCode;
    private Integer sectionCode;

    // Isso vai virar uma lista de BatchDTO
    private List<Object> batches;
}