package br.com.mercadolivre.projetointegrador.warehouse.exception.db;

public class ProductAlreadyExists extends Exception {

  public ProductAlreadyExists(String message) {
    super(message);
  }
}
