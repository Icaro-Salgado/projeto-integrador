package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SectionResponseDTO {

    @JsonView(SectionView.Detail.class)
    private Long id;

    @JsonView(SectionView.Detail.class)
    private String warehouse; // TODO: change to entity

    @JsonView(SectionView.Detail.class)
    private String manager; // TODO: change to entity

    @JsonView(SectionView.Detail.class)
    private BigDecimal maximumTemperature;

    @JsonView(SectionView.Detail.class)
    private BigDecimal minimumTemperature;

    @JsonView(SectionView.Detail.class)
    private Integer capacity;

    @JsonView(SectionView.Detail.class)
    private Date createdAt;

    @JsonView(SectionView.Detail.class)
    private List<Map<String, String>> links;
}
