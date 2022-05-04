package br.com.mercadolivre.projetointegrador.warehouse.assembler;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductInWarehouseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.ProductInWarehouseMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.ProductInWarehouses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductInWarehousesAssembler {
    public ResponseEntity<ProductInWarehouseDTO> toResponse(ProductInWarehouses entity){
        ProductInWarehouseDTO dto = ProductInWarehouseMapper.INSTANCE.toDTO(entity);

        return ResponseEntity.ok(dto);
    }
}
