package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    uniqueConstraints = {
      @UniqueConstraint(
          name = "UniqueBatchAndSeller",
          columnNames = {"seller_id", "batchNumber"})
    })
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Batch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column private Long section_id;

  @Column private Long seller_id;

  @Column private BigDecimal price;

  @Column private Integer order_number;

  @Column private Integer batchNumber;

  @Column private Integer quantity;

  @Column private LocalDate manufacturing_datetime;

  @Column private LocalDate due_date;

  @Column @CreatedDate private LocalDate created_at;
}
