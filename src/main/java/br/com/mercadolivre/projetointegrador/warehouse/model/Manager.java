package br.com.mercadolivre.projetointegrador.warehouse.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "managers")
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
