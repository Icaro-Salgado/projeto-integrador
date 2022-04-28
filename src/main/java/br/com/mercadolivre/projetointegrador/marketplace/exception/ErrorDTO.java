package br.com.mercadolivre.projetointegrador.marketplace.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {

  private String error;
  private String message;
}
