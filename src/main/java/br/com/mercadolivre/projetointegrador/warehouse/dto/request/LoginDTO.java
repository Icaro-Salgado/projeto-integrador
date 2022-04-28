package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class LoginDTO {

    @NotNull
    @NotBlank
    private final String user;

    @NotNull
    @NotBlank
    private final String password;
}
