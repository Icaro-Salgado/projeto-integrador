package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.warehouse.model.Section;

import java.math.BigDecimal;
import java.util.Calendar;

public class SectionServiceTestUtils {

    public static Section getMockSection(){
        Calendar calendar  = Calendar.getInstance();
        calendar.set(2022, Calendar.JANUARY, 1);

        return Section
                .builder()
                .id(1L)
                .warehouse("wh1")
                .manager("m1")
                .maximumTemperature(BigDecimal.valueOf(25.33))
                .minimumTemperature(BigDecimal.valueOf(15.33))
                .capacity(100)
                .createdAt(calendar.getTime())
                .build();
    }
}
