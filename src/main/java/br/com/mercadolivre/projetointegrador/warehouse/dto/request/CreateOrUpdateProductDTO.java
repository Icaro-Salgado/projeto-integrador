package br.com.mercadolivre.projetointegrador.warehouse.dto.request;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.InvalidCategoryException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CreateOrUpdateProductDTO {

  @NotEmpty(message = "nome do produto é obrigatório.")
  private String name;

  private String category;

  public Product mountProduct() throws InvalidCategoryException {
    boolean validCategory = CategoryEnum.contains(category);

    if (!validCategory) {
      throw new InvalidCategoryException("Verifique a categoria informada.");
    }

    Product product = new Product();
    product.setName(name);
    product.setCategory(CategoryEnum.valueOf(category));
    product.setCreated_at(null);

    return product;
  }
}
