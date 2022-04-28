package br.com.mercadolivre.projetointegrador.warehouse.model;

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
