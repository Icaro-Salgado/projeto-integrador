package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RegisterDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppUserMapper {

  AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

  AppUser toModel(RegisterDTO newUser);
}
