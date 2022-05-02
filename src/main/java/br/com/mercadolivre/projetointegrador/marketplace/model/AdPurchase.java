package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "ad_purchase")
@Getter @Setter
@NoArgsConstructor
public class AdPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ad_id", referencedColumnName = "id", nullable = false)
    private Ad ad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false)
    private Purchase purchase;

    private Integer quantity;

    private BigDecimal price;

    private Integer discount;

}
