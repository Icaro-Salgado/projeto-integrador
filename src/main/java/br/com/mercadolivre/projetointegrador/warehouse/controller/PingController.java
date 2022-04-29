package br.com.mercadolivre.projetointegrador.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Ping")
public class PingController {

  @Operation(summary = "RETORNA UMA STRING", description = "Retorna a string de teste 'pong' ")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Ping retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ping não encontrado")
      })
  @GetMapping("/ping")
  public String pong() {
    return "pong";
  }
}
