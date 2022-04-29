package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column
    private String name;

    @Column
    private int quantity;

    @Column
    private BigDecimal price;

    @Column
    private int discount;

    @Column
    private String category;

    @Column
    private LocalDate manufacturing_date;

    @Column
    private LocalDate due_date;

}
