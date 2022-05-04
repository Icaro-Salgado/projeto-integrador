package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import br.com.mercadolivre.projetointegrador.marketplace.enums.PurchaseStatusCodeEnum;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseResponseDTO {

  private Long purchaseId;
  private PurchaseStatusCodeEnum statusCode;
  private List<PurchaseProductResponseDTO> products;
  private BigDecimal total;

  public PurchaseResponseDTO modelToDTO(Purchase purchase) {
    PurchaseResponseDTO purchaseResponse = new PurchaseResponseDTO();
    purchaseResponse.setPurchaseId(purchase.getId());
    purchaseResponse.setStatusCode(purchase.getStatusCode());
    purchaseResponse.setProducts(products);
    purchaseResponse.setTotal(purchase.getTotal());

    return purchaseResponse;
  }
}
