package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class WarehouseTestUtils {
  public static List<Batch> getBatch() {
    List<Batch> batches = new ArrayList<>();

    Product product = new Product(1l, "alface", CategoryEnum.FS, null);

    LocalDate localDate = LocalDate.now();
    Date date = new Date();

    Batch batch1 =
        new Batch(
            1l,
            product,
            Section.builder().id(2L).warehouse(Warehouse.builder().id(2L).build()).build(),
            AppUser.builder().id(3L).build(),
            new BigDecimal(30.0),
            12345,
            250422,
            4,
            null,
            null,
            null);

    Batch batch2 =
        new Batch(
            2l,
            product,
            Section.builder().id(3L).warehouse(Warehouse.builder().id(3L).build()).build(),
            AppUser.builder().id(4L).build(),
            new BigDecimal(36.0),
            12346,
            250423,
            5,
            null,
            null,
            null);

    batches.add(batch1);
    batches.add(batch2);

    return batches;
  }

  public static Batch getBatch1() {

    Product product = new Product(1l, "alface", CategoryEnum.FS, null);

    Batch batch1 =
        new Batch(
            1l,
            product,
            Section.builder().id(2L).warehouse(Warehouse.builder().id(2L).build()).build(),
            AppUser.builder().id(3L).build(),
            new BigDecimal(30.0),
            12345,
            250422,
            4,
            null,
            null,
            null);

    return batch1;
  }

  public static Batch getBatch2() {

    Product product = new Product(1l, "alface", CategoryEnum.FS, null);

    Batch batch2 =
        new Batch(
            2l,
            product,
            Section.builder().id(3L).warehouse(Warehouse.builder().id(3L).build()).build(),
            AppUser.builder().id(4L).build(),
            new BigDecimal(36.0),
            12346,
            250423,
            5,
            null,
            null,
            null);

    return batch2;
  }

  public static Section getSection() {
    return Section.builder().id(1l).build();
  }

  public static InboundOrder getInboundOrder() {
    return InboundOrder.builder()
        .orderNumber(12345)
        .warehouseCode(2L)
        .sectionCode(2L)
        .batches(getBatch())
        .build();
  }

  public static ProductInWarehouses getProductInWarehouse(){
    Product product = new Product(1l, "alface", CategoryEnum.FS, null);


    List<ProductInWarehouse> productsSum = new ArrayList<>();
    Map<Long, Integer> productQtyToSum = getBatch().stream()
            .collect(
                    groupingBy(b-> b.getSection().getWarehouse().getId(), summingInt(Batch::getQuantity))
            );

    for (Map.Entry<Long,Integer> item:productQtyToSum.entrySet()
    ) {
      productsSum.add(
              ProductInWarehouse.builder()
                      .warehouseId(item.getKey())
                      .productQty(item.getValue())
                      .build()
      );
    }

    return ProductInWarehouses.builder()
            .productId(product.getId())
            .warehouses(productsSum)
            .build();
  }


}
