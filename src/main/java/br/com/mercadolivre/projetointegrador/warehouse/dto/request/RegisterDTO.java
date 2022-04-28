package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
@AllArgsConstructor
public class RegisterDTO {

    private String email;
    private String name;
    private String userName;
    private String password;
}
