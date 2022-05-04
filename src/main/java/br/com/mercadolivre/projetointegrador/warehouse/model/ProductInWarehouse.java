package br.com.mercadolivre.projetointegrador.warehouse.model;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInWarehouse {
    private Long warehouseId;
    private Integer productQty;
}
