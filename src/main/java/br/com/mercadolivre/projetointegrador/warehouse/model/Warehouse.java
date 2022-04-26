package br.com.mercadolivre.projetointegrador.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    //@OneToMany
    private Long section;

    public Warehouse(String name, Location location) {
        this.name = name;
        this.location = location;
    }
}
