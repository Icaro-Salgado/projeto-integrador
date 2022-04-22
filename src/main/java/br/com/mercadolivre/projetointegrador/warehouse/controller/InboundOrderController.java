package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inboundorder")
public class InboundOrderController {

    @PostMapping
    public ResponseEntity<?> addInboundOrder(@RequestBody InboundOrderDTO dto) {
        // TODO: Converter DTO para model
        // TODO: Salvar o InboundOrder
        // TODO: Converter o retorno para DTO
        // TODO: Montar o EntityModel
        // TODO: Retornar o created com a URI
        return null;
    }

    // Aqui podemos fazer um put em /{id} ou na raiz, se for na raíz o ID tem que vir no DTO
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInboundOrder(@RequestBody InboundOrderDTO dto) {
        // TODO: Converte DTO para Model
        // TODO: Atualizar o InboundOrder
        // TODO: Converter o retorno para DTO
        // TODO: Montar o EntityModel
        // TODO: Retornar created com a URI
       return null;
    }
}
