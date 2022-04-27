package br.com.mercadolivre.projetointegrador.marketplace.dto;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.marketplace.exception.InvalidCategoryException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
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