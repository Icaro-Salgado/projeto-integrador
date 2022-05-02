package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class CategoryListDTO {
    private String name;
    private String code;
}
