package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = BatchMapper.class)
public interface InboundOrderMapper {

    InboundOrderMapper INSTANCE = Mappers.getMapper(InboundOrderMapper.class);

    InboundOrder toModel(InboundOrderDTO inboundOrder);
}