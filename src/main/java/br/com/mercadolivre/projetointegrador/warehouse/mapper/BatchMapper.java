package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ProductMapper.class, AppUserMapper.class})
public interface BatchMapper {
  BatchMapper INSTANCE = Mappers.getMapper(BatchMapper.class);

  @Mapping(source = "section.id", target = "section_id")
  List<BatchResponseDTO> toResponseDTOList(List<Batch> batch);

  @Mapping(source = "section.id", target = "section_id")
  @Mapping(source = "dueDate", target = "dueDate")
  BatchResponseDTO toResponseDTO(Batch batch);
}
