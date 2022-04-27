package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WarehouseTestUtils {

    public static List<Batch> getBatch(){
        List<Batch> batches = new ArrayList<>();

        LocalDate localDate = LocalDate.now();
        Product product = new Product(1l,"alface","verdura",localDate);

        Batch batch1 = new Batch(1l, product, 2l, 3l,new BigDecimal(30.0),
                12345,250422,4, localDate, localDate,localDate);

        Batch batch2 = new Batch(2l, product, 3l, 4l,new BigDecimal(36.0),
                12346,250423,5, localDate, localDate,localDate);

        batches.add(batch1);
        batches.add(batch2);

        return batches;
    }

    public static Batch getBatch1(){

        LocalDate localDate = LocalDate.now();
        Product product = new Product(1l,"alface","verdura",localDate);

        Batch batch1 = new Batch(1l, product, 2l, 3l,new BigDecimal(30.0),
                12345,250422,4, localDate, localDate,localDate);

        return batch1;
    }

    public static Batch getBatch2(){

        LocalDate localDate = LocalDate.now();
        Product product = new Product(1l,"alface","verdura",localDate);

        Batch batch2 = new Batch(2l, product, 3l, 4l,new BigDecimal(36.0),
                12346,250423,5, localDate, localDate,localDate);

        return batch2;
    }

    public static Section getSection(){
        return Section.builder().id(1l).build();
    }

    public static InboundOrder getInboundOrder(){
        return InboundOrder.builder().orderNumber(12345).warehouseCode(6l).sectionCode(7l).batches(getBatch()).build();
    }

}
