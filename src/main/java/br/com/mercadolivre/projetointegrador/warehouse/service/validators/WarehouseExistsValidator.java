package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.exception.db.WarehouseNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;

import java.util.Optional;


public class WarehouseExistsValidator implements WarehouseValidator{

    private final Long warehouseId;
    private final WarehouseRepository warehouseRepository;

    public WarehouseExistsValidator(Long warehouseId, WarehouseRepository warehouseRepository) {
        this.warehouseId = warehouseId;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void Validate() {
        Optional<Warehouse> warehouseRegistered = warehouseRepository.findById(warehouseId);

        if (warehouseRegistered.isEmpty()) throw new WarehouseNotFoundException("Warehouse not Found!");
    }
}
