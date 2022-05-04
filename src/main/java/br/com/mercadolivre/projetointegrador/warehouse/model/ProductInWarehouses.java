package br.com.mercadolivre.projetointegrador.warehouse.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductInWarehouses {

  private Long productId;
  private List<ProductInWarehouse> warehouses;
}
