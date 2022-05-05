package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseDTO {

  @NotNull(message = "Informe o anúncio corretamente.")
  Long adId;

  @Min(value = 1, message = "Quantidade mínima é um.")
  Integer quantity;
}
