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

        Product product = new Product(1l,"alface","verdura",null);

        Batch batch1 = new Batch(1l, product, 2l, 3l,new BigDecimal(30.0),
                12345,250422,4, null, null,null);

<<<<<<< Updated upstream
        Batch batch2 = new Batch(2l, product, 3l, 4l,new BigDecimal(36.0),
                12346,250423,5, null, null,null);
=======
        Batch batch2 = new Batch(2l, product, 2l, 4l,new BigDecimal(36.0),
                12346,250423,5, localDate, localDate,localDate);
>>>>>>> Stashed changes

        batches.add(batch1);
        batches.add(batch2);

        return batches;
    }

<<<<<<< Updated upstream
    public static Batch getBatch1(){

        Product product = new Product(1l,"alface","verdura",null);

        Batch batch1 = new Batch(1l, product, 2l, 3l,new BigDecimal(30.0),
                12345,250422,4, null, null,null);

        return batch1;
    }

    public static Batch getBatch2(){

        Product product = new Product(1l,"alface","verdura",null);

        Batch batch2 = new Batch(2l, product, 3l, 4l,new BigDecimal(36.0),
                12346,250423,5, null, null,null);

        return batch2;
    }

=======
>>>>>>> Stashed changes
    public static Section getSection(){
        return Section.builder().id(1l).build();
    }

    public static InboundOrder getInboundOrder(){
        return InboundOrder.builder().orderNumber(12345).warehouseCode(6l).sectionCode(2l).batches(getBatch()).build();
    }

}
