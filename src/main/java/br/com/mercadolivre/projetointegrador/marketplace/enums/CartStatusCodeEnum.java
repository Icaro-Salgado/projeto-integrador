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
}
