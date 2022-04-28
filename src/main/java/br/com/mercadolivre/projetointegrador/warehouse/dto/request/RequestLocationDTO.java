package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter @Setter
@Builder
public class RequestLocationDTO {

    @NotNull
    @NotBlank
    private String country;

    @NotNull
    @NotBlank
    private String state;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String neighborhood;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    private Integer number;

    @NotNull
    private Integer zipcode;

}
