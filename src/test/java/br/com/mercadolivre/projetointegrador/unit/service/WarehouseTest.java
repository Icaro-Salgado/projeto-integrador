package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.test_utils.WarehouseTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseTest {

    @Mock
    private BatchService batchService;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    @Test
    public void TestIfSaveBatchInSection() throws NotFoundException {

        List<Batch> expected = WarehouseTestUtils.getBatch();

        Mockito.when(sectionRepository.findById(Mockito.any()))
                .thenReturn((Optional.of(WarehouseTestUtils.getSection())));

        Mockito.doNothing().when(batchService).createBatch(Mockito.any());

        List<Batch> result = warehouseService.saveBatchInSection(WarehouseTestUtils.getInboundOrder());
        assertEquals(expected, result);
    }

    @Test
    public void TestIfupdateBatchInSection() throws NotFoundException {

        List<Batch> expected = WarehouseTestUtils.getBatch();

        Mockito.when(sectionRepository.findById(Mockito.any()))
                .thenReturn((Optional.of(WarehouseTestUtils.getSection())));

        Mockito.doNothing().when(batchService).createBatch(Mockito.any());

        List<Batch> result = warehouseService.updateBatchInSection(WarehouseTestUtils.getInboundOrder());
        assertEquals(expected, result);
    }
}
