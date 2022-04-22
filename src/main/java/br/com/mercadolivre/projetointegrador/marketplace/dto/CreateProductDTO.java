package br.com.mercadolivre.projetointegrador.marketplace.dto;

import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class CreateProductDTO {

    @NotEmpty(message = "nome do producto é obrigatório.")
    private String name;

    @NotEmpty(message = "categoria é obrigatório.")
    private String category;

    @Min(value = 0, message = "O preço não pode ser menor do que zero.")
    private BigDecimal price;

    public Product mountProduct() {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setCreated_at(LocalDate.now());

        return product;
    }

}