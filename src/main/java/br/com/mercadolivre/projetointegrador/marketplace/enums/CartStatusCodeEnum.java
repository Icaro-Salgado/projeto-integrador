package br.com.mercadolivre.projetointegrador.marketplace.enums;

public enum CartStatusCodeEnum {
  ABERTO,
  FINALIZADO;

  public static boolean contains(String status) {
    for (CartStatusCodeEnum c : CartStatusCodeEnum.values()) {
      if (c.name().equals(status)) {
        return true;
      }
    }

    return false;
  }

  public static CartStatusCodeEnum switchStatus(CartStatusCodeEnum cartStatus) {
    CartStatusCodeEnum statusCodeEnum = null;
    switch (cartStatus) {
      case ABERTO:
        statusCodeEnum = CartStatusCodeEnum.FINALIZADO;
        break;
      case FINALIZADO:
        statusCodeEnum = CartStatusCodeEnum.ABERTO;
        break;
    }
    return statusCodeEnum;
  }
}
