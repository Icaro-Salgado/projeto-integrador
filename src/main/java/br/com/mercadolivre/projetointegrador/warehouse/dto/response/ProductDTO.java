package br.com.mercadolivre.projetointegrador.warehouse.dto.response;


import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.view.ProductView;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
public class ProductDTO {

    @JsonView(ProductView.Detail.class)
    private Long id;

    @JsonView({ProductView.Detail.class, ProductView.List.class})
    private String name;

    @JsonView({ProductView.Detail.class, ProductView.List.class})
    private CategoryEnum category;

    @JsonView(ProductView.Detail.class)
    private Date created_at;

    @JsonView({ProductView.Detail.class, ProductView.List.class})
    private List<Map<String, String>> links;
}
