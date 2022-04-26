package br.com.mercadolivre.projetointegrador.warehouse.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String warehouse; // TODO: change to entity

    @Column
    private String manager; // TODO: change to entity

    @Column(nullable = false, name = "maximum_temperature")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal maximumTemperature;

    @Column(nullable = false, name = "minimum_temperature")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal minimumTemperature;

    @Column(nullable = false)
    private Integer capacity;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
