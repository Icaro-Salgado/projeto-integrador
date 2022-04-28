package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.assembler.SectionAssembler;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateSectionPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.service.SectionService;
import br.com.mercadolivre.projetointegrador.warehouse.view.SectionView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/section")
public class SectionController {

  private final SectionService sectionService;
  private final SectionAssembler assembler;

  @JsonView(SectionView.Detail.class)
  @GetMapping("{id}")
  public ResponseEntity<SectionResponseDTO> findById(@PathVariable Long id) {
    Section section = sectionService.findSectionById(id);

    return assembler.toResponse(section, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SectionResponseDTO> createSection(
      @RequestBody @Valid CreateSectionPayloadDTO payload) {
    Section created = sectionService.createSection(payload);

    return assembler.toResponse(created, HttpStatus.CREATED);
  }
}
