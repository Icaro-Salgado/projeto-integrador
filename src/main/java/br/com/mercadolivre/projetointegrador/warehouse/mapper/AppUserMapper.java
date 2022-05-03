package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RegisterDTO;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.UserResponseDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppUserMapper {

  AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

  AppUser toModel(RegisterDTO newUser);

  @Mapping(target = "userName", source = "username")
  UserResponseDTO toResponseDTO(AppUser user);
}
