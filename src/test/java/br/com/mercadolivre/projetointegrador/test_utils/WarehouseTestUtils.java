package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarehouseTestUtils {

    public static List<Batch> getBatch() {
        List<Batch> batches = new ArrayList<>();

        Product product = new Product(1l, "alface", CategoryEnum.FS, null);

        LocalDate localDate = LocalDate.now();
        Date date =  new Date();

        Batch batch1 = new Batch(1l, product, 2l, 3l,new BigDecimal(30.0),
                12345,250422,4, localDate, localDate,localDate);

        Batch batch2 = new Batch(2l, product, 2l, 4l,new BigDecimal(36.0),
                12346,250423,5, localDate, localDate,localDate);


        batches.add(batch1);
        batches.add(batch2);

        return batches;
    }

    public static Section getSection(){
        return Section.builder().id(1l).build();
    }

    public static InboundOrder getInboundOrder(){
        return InboundOrder.builder().orderNumber(12345).warehouseCode(6l).sectionCode(2l).batches(getBatch()).build();
    }

}
