package br.com.mercadolivre.projetointegrador.marketplace.dto;


import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrUpdateBatchDTO {

    private Product product;
    private Section section;
    private Long seller_id;
    private BigDecimal price;
    private Integer order_number;
    private Integer batch_number;
    private Integer quantity;
    private LocalDate manufacturing_datetime;
    private LocalDate due_date;

    public Batch mountBatch() {
        Batch batch = new Batch();
        batch.setProduct(product);
        batch.setSection(section);
        batch.setSeller_id(seller_id);
        batch.setPrice(price);
        batch.setOrder_number(order_number);
        batch.setBatch_number(batch_number);
        batch.setQuantity(quantity);
        batch.setManufacturing_datetime(manufacturing_datetime);
        batch.setDue_date(due_date);
        batch.setCreated_at(LocalDate.now());

        return batch;
    }

}