package br.com.mercadolivre.projetointegrador.marketplace.model;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private CategoryEnum category;

    @Column
    @CreatedDate
    private Date created_at;

}
