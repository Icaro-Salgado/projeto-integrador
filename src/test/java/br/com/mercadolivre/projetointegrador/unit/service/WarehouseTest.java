package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.test_utils.WarehouseTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.WarehouseValidatorExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WarehouseTest {

    @Mock
    private BatchService batchService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private WarehouseValidatorExecutor warehouseValidatorExecutor;

    @InjectMocks
    private WarehouseService warehouseService;


    @Test
    public void TestIfSaveBatchInSection() throws NotFoundException {

        List<Batch> expected = WarehouseTestUtils.getBatch();

        Mockito.doNothing().when(batchService).createBatch(Mockito.any());

        List<Batch> result = warehouseService.saveBatchInSection(WarehouseTestUtils.getInboundOrder());
        assertEquals(expected, result);
    }

    @Test
    public void TestIfupdateBatchInSection() throws NotFoundException {


        List<Batch> expected = List.of(WarehouseTestUtils.getBatch1(), WarehouseTestUtils.getBatch2());

        Mockito.when(batchService.updateBatchByBatchNumber(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        List<Batch> result = warehouseService.updateBatchInSection(WarehouseTestUtils.getInboundOrder());

        assertEquals(expected, result);
    }
}
