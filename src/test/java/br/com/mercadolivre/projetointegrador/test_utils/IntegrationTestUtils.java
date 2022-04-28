package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.Location;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IntegrationTestUtils {

    @Autowired private WarehouseRepository warehouseRepository;

    @Autowired private SectionRepository sectionRepository;

    public Warehouse createWarehouse() {
        Warehouse warehouse =
                Warehouse.builder()
                        .name("Mocked warehouse")
                        .location(
                                new Location(
                                        "Brazil",
                                        "SP",
                                        "Osasco",
                                        "Bomfim",
                                        "Av. das Nações Unidas",
                                        3003,
                                        6233200))
                        .build();

        return warehouseRepository.save(warehouse);
    }

    public Section createSection() {
        Warehouse warehouse = createWarehouse();

        return sectionRepository.save(
                new Section(
                        null,
                        warehouse,
                        "m1",
                        BigDecimal.valueOf(33.33),
                        BigDecimal.ZERO,
                        1000,
                        CategoryEnum.FS,
                        null));
    }
}
