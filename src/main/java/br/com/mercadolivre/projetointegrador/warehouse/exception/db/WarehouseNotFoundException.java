package br.com.mercadolivre.projetointegrador.warehouse.exception.db;

public class WarehouseNotFoundException extends RuntimeException {

  public WarehouseNotFoundException(String msg) {
    super(msg);
  }
}
