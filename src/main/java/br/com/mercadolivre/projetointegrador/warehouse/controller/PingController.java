package br.com.mercadolivre.projetointegrador.warehouse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

  @GetMapping("/ping")
  public String pong() {
    return "pong";
  }
}
