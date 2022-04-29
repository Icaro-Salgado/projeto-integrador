package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BatchMapper {
  BatchMapper INSTANCE = Mappers.getMapper(BatchMapper.class);

  @Mapping(source = "section.id", target = "section_id")
  BatchResponseDTO toResponseDTO(Batch batch);
}
