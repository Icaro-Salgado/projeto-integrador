package br.com.mercadolivre.projetointegrador.marketplace.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Customer {

    private Long id;
    private String name;
    private String email;
    private String password;

}
