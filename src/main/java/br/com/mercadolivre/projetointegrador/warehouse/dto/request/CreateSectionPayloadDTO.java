package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter @Setter
public class CreateSectionPayloadDTO {

    @NotNull
    private Long warehouseId;

    @NotNull
    private Long managerId;

    @Digits(integer = 3, fraction = 2)
    private BigDecimal minimumTemperature;

    @Digits(integer = 3, fraction = 2)
    private BigDecimal maximumTemperature;

    @NotNull
    @Min(10)
    private Integer capacity;

    @Column
    @NotNull
    private CategoryEnum productCategory;
}
