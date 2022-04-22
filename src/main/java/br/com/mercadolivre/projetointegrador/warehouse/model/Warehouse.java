package br.com.mercadolivre.projetointegrador.warehouse.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    //@OneToMany
    private Long section;
}
