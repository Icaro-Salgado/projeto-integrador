package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "ad_batches")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AdBatch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ad_id", referencedColumnName = "id", nullable = false)
  private Ad ad;

  @Column(name = "batch_id")
  private Integer batchId;
}
