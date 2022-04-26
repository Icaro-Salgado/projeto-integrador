package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class WarehouseServiceTests {
    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    @DisplayName("Test if correct batch is returned on find product batches method")
    public void TestIfReturnsRightBatchesOnFindProduct() {

        // SETUP
        Manager requestManager = Manager.builder().id(1L).build();

        // ACT

        // ASSERT

        assert true;
    }
}
