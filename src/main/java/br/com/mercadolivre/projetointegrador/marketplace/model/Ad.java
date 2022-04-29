package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class Ad {

    private Long id;
    private List<Long> batches;
    private Long seller_id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private int discount;
    private String category;
    private LocalDate manufacturing_date;
    private LocalDate due_date;

}
