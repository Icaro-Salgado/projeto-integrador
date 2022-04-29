package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BatchMapper {
  BatchMapper INSTANCE = Mappers.getMapper(BatchMapper.class);

  BatchResponseDTO toResponseDTO(Batch batch);
}
