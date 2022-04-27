package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CreatedBatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BatchMapper {
    BatchMapper INSTANCE = Mappers.getMapper(BatchMapper.class);

    CreatedBatchDTO toCreatedDTO(Batch batch);

    Batch mapBatch(CreateBatchPayloadDTO createBatchPayloadDTO);

    CreateBatchPayloadDTO mapBatchModel(Batch value);
}
