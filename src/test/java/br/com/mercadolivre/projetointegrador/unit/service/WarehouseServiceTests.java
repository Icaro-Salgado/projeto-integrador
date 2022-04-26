package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class WarehouseServiceTests {
    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    public void TestIfReturnsRightBatchesOnFindProduct() {

        // SETUP
        Manager requestManager = Manager.builder().id(1L).build();

        // ACT

        // ASSERT

        assert false;
    }
}
