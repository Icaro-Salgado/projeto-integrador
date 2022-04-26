package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column
    private Long section_id;

    @Column
    private Long seller_id;

    @Column
    private BigDecimal price;

    @Column
    private Integer order_number;

    @Column
    private Integer batch_number;

    @Column
    private Integer quantity;

    @Column
    private LocalDate manufacturing_datetime;

    @Column
    private LocalDate due_date;

    @Column
    private LocalDate created_at;
}