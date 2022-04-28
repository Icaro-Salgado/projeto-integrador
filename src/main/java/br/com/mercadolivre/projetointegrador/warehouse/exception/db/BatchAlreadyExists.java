package br.com.mercadolivre.projetointegrador.warehouse.exception.db;

public class BatchAlreadyExists extends RuntimeException {

  public BatchAlreadyExists(String message) {
    super(message);
  }
}
