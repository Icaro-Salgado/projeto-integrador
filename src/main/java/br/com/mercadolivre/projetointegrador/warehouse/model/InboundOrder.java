package br.com.mercadolivre.projetointegrador.warehouse.model;

import lombok.Data;

import java.util.List;

@Data
public class InboundOrder {

    private Integer orderNumber;
    private Long warehouseCode;
    private Integer sectionCode;

    //TODO: Trocar para list<Batch>
    private List<Object> batches;


}
