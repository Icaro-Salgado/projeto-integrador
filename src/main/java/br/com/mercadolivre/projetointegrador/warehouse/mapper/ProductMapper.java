package br.com.mercadolivre.projetointegrador.warehouse.mapper;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductResponseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  ProductDTO toDto(Product product);

  ProductResponseDTO toResponseDTO(Product product);

  List<ProductDTO> toDto(List<Product> products);
}
