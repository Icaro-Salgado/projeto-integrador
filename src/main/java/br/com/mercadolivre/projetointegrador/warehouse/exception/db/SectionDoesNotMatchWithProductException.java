package br.com.mercadolivre.projetointegrador.warehouse.exception.db;

public class SectionDoesNotMatchWithProductException extends RuntimeException {
  public SectionDoesNotMatchWithProductException(String message) {
    super(message);
  }
}
