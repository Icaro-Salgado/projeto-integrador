package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductInWarehouseDTO;

import br.com.mercadolivre.projetointegrador.warehouse.model.ProductInWarehouses;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductInWarehouseMapper {
  ProductInWarehouseMapper INSTANCE = Mappers.getMapper(ProductInWarehouseMapper.class);

  public ProductInWarehouseDTO toDTO(ProductInWarehouses model);
}
