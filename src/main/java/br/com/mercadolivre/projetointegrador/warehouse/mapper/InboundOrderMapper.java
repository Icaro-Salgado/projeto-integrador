package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InboundOrderMapper {

    InboundOrderMapper INSTANCE = Mappers.getMapper(InboundOrderMapper.class);

    InboundOrderDTO toDTO();
}