package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrUpdateAdDTO {

  @NotEmpty(message = "O produto deve pertencer a pelo menos um lote.")
  private List<Long> batchesId;

  @NotEmpty(message = "O campo nome deve ser preenchido.")
  private String name;

  @Min(value = 1, message = "Deve conter ao menos um produto para ser vendido.")
  private int quantity;

  private BigDecimal price;

  @Min(value = 0, message = "O desconto não pode ser negativo.")
  private int discount;

  private String category;
}
