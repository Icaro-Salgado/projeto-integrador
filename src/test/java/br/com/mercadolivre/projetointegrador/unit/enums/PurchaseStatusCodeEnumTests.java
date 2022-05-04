package br.com.mercadolivre.projetointegrador.unit.enums;

import br.com.mercadolivre.projetointegrador.marketplace.enums.PurchaseStatusCodeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseStatusCodeEnumTests {
  PurchaseStatusCodeEnum purchaseStatusCodeEnum;

  @Test
  public void validateAberto() {
    assertTrue(PurchaseStatusCodeEnum.contains("ABERTO"));
  }

  @Test
  public void validateFinalizado() {
    assertTrue(PurchaseStatusCodeEnum.contains("FINALIZADO"));
  }
}
