package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.warehouse.model.Location;
import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ManagerRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;

@Component
public class SectionServiceTestUtils {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public static Section getMockSection(){
        Calendar calendar  = Calendar.getInstance();
        calendar.set(2022, Calendar.JANUARY, 1);

        return Section
                .builder()
                .id(1L)
                .warehouse(null)
                .manager(null)
                .maximumTemperature(BigDecimal.valueOf(25.33))
                .minimumTemperature(BigDecimal.valueOf(15.33))
                .capacity(100)
                .createdAt(calendar.getTime())
                .build();
    }

    public Section getIntegrationMockSection(){
        Warehouse warehouse = warehouseRepository.save(
                new Warehouse(
                        "warehouse 1",
                        new Location(
                                "Brazil",
                                "SP",
                                "mockedCity",
                                "mocked neighborhood",
                                "mocked street",
                                3200,
                                11234567
                        )

                )
        );

        Section section = getMockSection();
        section.setWarehouse(warehouse);

        return section;
    }
}
