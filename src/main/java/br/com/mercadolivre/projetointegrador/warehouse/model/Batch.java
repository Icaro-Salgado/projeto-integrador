package br.com.mercadolivre.projetointegrador.warehouse.model;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    },
    indexes = {
            @Index(name = "batchNumberIdx", columnList = "batchNumber")
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

  @ManyToOne
  @JoinColumn(name = "section_id", nullable = false)
  private Section section;

  @ManyToOne
  @JoinColumn(name = "seller_id", nullable = false)
  @JsonIgnoreProperties("password")
  private AppUser seller;

  @Column private BigDecimal price;

  @Column private Integer order_number;

  @Column private Integer batchNumber;

  @Column private Integer quantity;

  @Column private LocalDate manufacturing_datetime;

  @Column private LocalDate dueDate;

  @Column @CreatedDate private LocalDate created_at;
}
