package br.com.mercadolivre.projetointegrador.marketplace.exceptions;

public class OutOfStockException extends Exception {

  public OutOfStockException(String message) {
    super(message);
  }
}
