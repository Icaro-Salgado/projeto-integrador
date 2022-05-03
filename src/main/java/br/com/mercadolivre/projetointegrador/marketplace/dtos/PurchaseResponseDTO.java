package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class PurchaseResponseDTO {

    private Long purchaseId;
    private String statusCode;
    private List<PurchaseProductResponseDTO> products;
    private BigDecimal total;

}
