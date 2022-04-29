package br.com.mercadolivre.projetointegrador.warehouse.enums;

public enum SortTypeEnum {
  L("batchNumber"),
  C("quantity"),
  F("due_date");

  public final String field;

  private SortTypeEnum(String field) {
    this.field = field;
  }
}
