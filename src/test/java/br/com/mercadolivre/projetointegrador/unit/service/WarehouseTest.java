package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.test_utils.WarehouseTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.WarehouseValidatorExecutor;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseTest {

//    @Mock
//    private InboundOrder inboundOrder;

    @Mock
    private BatchService batchService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private WarehouseValidatorExecutor warehouseValidatorExecutor;

    @InjectMocks
    private WarehouseService warehouseService;

//    TODO: TestIfSaveBatchInSection
//    @Test
//    public void TestIfSaveBatchInSection() {
//
//    }

    @Test
    public void TestIfupdateBatchInSection() throws NotFoundException {

        List<Batch> expected = List.of(WarehouseTestUtils.getBatch1(), WarehouseTestUtils.getBatch2());

        Mockito.doNothing().when(batchService).updateBatchByBatchNumber(Mockito.any());

        List<Batch> result = warehouseService.updateBatchInSection(WarehouseTestUtils.getInboundOrder());



        assertEquals(expected, result);
    }
}
