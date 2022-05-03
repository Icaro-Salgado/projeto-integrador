package br.com.mercadolivre.projetointegrador.unit.enums;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CartStatusCodeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartStatusCodeEnumTests {

  CartStatusCodeEnum cartStatusCodeEnum;

  @Test
  public void validateABERTO() {
    assertTrue(CartStatusCodeEnum.contains("ABERTO"));
  }

  @Test
  public void validateFINALIZADO() {
    assertTrue(CartStatusCodeEnum.contains("FINALIZADO"));
  }

  @Test
  public void testSwitchStatus() {
    CartStatusCodeEnum status = CartStatusCodeEnum.ABERTO;
    CartStatusCodeEnum switchedStatus = CartStatusCodeEnum.switchStatus(status);

    assertNotEquals(status, switchedStatus);
  }
}
