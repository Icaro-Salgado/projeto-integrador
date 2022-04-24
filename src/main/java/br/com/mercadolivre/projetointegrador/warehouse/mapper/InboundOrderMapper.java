package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InboundOrderMapper {

    InboundOrderMapper INSTANCE = Mappers.getMapper(InboundOrderMapper.class);
}