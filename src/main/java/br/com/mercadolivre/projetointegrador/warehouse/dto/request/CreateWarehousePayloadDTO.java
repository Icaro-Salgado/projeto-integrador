package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class CreateWarehousePayloadDTO {

    @NotNull @NotBlank private String name;
    @Valid private RequestLocationDTO location;
}
