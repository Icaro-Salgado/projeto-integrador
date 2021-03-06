package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
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
  private List<Integer> batchesId;

  @NotEmpty(message = "O campo nome deve ser preenchido.")
  private String name;

  private int quantity;

  private BigDecimal price;

  @Min(value = 0, message = "O desconto não pode ser negativo.")
  private int discount;

  private CategoryEnum category;

  public Ad DTOtoModel() {
    Ad ad = new Ad();
    ad.setName(name);
    ad.setPrice(price);
    ad.setCategory(category);
    ad.setDiscount(discount);
    ad.setQuantity(quantity);

    return ad;
  }
}
