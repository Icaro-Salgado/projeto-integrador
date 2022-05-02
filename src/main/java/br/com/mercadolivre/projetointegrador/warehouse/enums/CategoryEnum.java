package br.com.mercadolivre.projetointegrador.warehouse.enums;

public enum CategoryEnum {
  FS("Fresh"),
  RF("Refresh"),
  FF("Frozen");

  private final String label;

  CategoryEnum(String label) {
    this.label = label;
  }

  public static boolean contains(String payload) {
    for (CategoryEnum c : CategoryEnum.values()) {
      if (c.name().equals(payload)) {
        return true;
      }
    }

    return false;
  }

  public String label(){
    return this.label;
  }
}
