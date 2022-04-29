package br.com.mercadolivre.projetointegrador.warehouse.exception.db;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
}
