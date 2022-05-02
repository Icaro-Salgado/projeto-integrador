package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CreatedBatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = ProductMapper.class)
public interface BatchMapper {
  BatchMapper INSTANCE = Mappers.getMapper(BatchMapper.class);

  CreatedBatchDTO toCreatedDTO(Batch batch);

  @Mapping(source = "section.id", target = "section_id")
  List<BatchResponseDTO> toResponseDTO(List<Batch> batch);


}
