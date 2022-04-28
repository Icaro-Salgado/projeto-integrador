package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RequestLocationDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Location;
import org.mapstruct.Mapper;

@Mapper
public interface LocationMapper {

  Location mapLocation(RequestLocationDTO dto);
}
