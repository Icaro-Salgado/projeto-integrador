package br.com.mercadolivre.projetointegrador.warehouse.enums;

public enum SortTypeEnum {
  L("batchNumber"),
  C("quantity"),
  F("dueDate");

  public final String field;

  private SortTypeEnum(String field) {
    this.field = field;
  }
}
