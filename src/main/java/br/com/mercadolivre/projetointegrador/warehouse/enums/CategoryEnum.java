package br.com.mercadolivre.projetointegrador.warehouse.enums;

public enum CategoryEnum {
  FS,
  RF,
  FF;

  public static boolean contains(String payload) {
    for (CategoryEnum c : CategoryEnum.values()) {
      if (c.name().equals(payload)) {
        return true;
      }
    }

    return false;
  }
}
