package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.exception.db.WarehouseNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;

import java.util.Optional;


public class WarehouseExistsValidator implements WarehouseValidator{

    private final Warehouse warehouse;
    private final WarehouseRepository warehouseRepository;

    public WarehouseExistsValidator(Warehouse warehouse, WarehouseRepository warehouseRepository) {
        this.warehouse = warehouse;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void Validate() {
        Optional<Warehouse> warehouseRegistered = warehouseRepository.findById(warehouse.getId());

        if (warehouseRegistered.isEmpty()) throw new WarehouseNotFoundException("Warehouse not Found!");
    }
}
