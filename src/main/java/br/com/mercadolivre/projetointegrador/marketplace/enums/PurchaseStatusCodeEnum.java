package br.com.mercadolivre.projetointegrador.marketplace.enums;

public enum PurchaseStatusCodeEnum {
  ABERTO,
  FINALIZADO;

  public static boolean contains(String status) {
    for (PurchaseStatusCodeEnum c : PurchaseStatusCodeEnum.values()) {
      if (c.name().equals(status)) {
        return true;
      }
    }

    return false;
  }
}
