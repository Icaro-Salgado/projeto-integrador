package br.com.mercadolivre.projetointegrador.warehouse.model;

import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Batch {

    private Long id;
    private Section section;
    private Product product;
    private Integer orderNumber;
    private Integer batchNumber;
    private Integer quantity;
    private Date manufacturingDate;
    private Date dueDate;
    private Date createdAt;
}
