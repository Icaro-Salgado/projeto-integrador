package br.com.mercadolivre.projetointegrador.warehouse.model;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InboundOrder {

  private Integer orderNumber;
  private Long warehouseCode;
  private Long sectionCode;
  private Long managerId;

  private List<Batch> batches;
}
