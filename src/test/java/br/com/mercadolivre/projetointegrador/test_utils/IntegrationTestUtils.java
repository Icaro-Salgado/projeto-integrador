package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseOrderDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.repository.RedisRepository;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.*;
import br.com.mercadolivre.projetointegrador.warehouse.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class IntegrationTestUtils {

  @Autowired private WarehouseRepository warehouseRepository;

  @Autowired private SectionRepository sectionRepository;

  @Autowired private ProductRepository productRepository;

  @Autowired private BatchRepository batchRepository;

  @Autowired private RedisRepository redisRepository;

  @Autowired private AppUserRepository appUserRepository;

  ObjectMapper objectMapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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
                    ThreadLocalRandom.current().nextInt(),
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
            1L,
            BigDecimal.valueOf(33.33),
            BigDecimal.ZERO,
            1000,
            CategoryEnum.FS,
            null));
  }

  public Product createProduct() {
    Product productMock = new Product(1L, "teste", CategoryEnum.FS, null);
    return productRepository.save(productMock);
  }

  public Batch createBatch() {
    Batch batch =
        Batch.builder()
            .product(createProduct())
            .section(createSection())
            .seller(createUser())
            .price(BigDecimal.TEN)
            .order_number(123)
            .batchNumber(9595)
            .quantity(10)
            .dueDate(LocalDate.now().plusWeeks(10))
            .build();

    return batchRepository.save(batch);
  }

  public Batch createBatch(Section section) {
    Batch batch =
        Batch.builder()
            .product(createProduct())
            .section(section)
            .seller(createUser())
            .price(BigDecimal.TEN)
            .order_number(123)
            .batchNumber(9595)
            .quantity(10)
            .build();

    return batchRepository.save(batch);
  }

  public PurchaseOrderDTO createPurchaseOrder() {
    PurchaseOrderDTO purchaseOrder = new PurchaseOrderDTO();

    CartProductDTO product1 = new CartProductDTO();
    product1.setProductId(1L);
    product1.setQuantity(5);
    product1.setUnitPrice(BigDecimal.valueOf(7.00));

    List<CartProductDTO> products = List.of(product1);
    purchaseOrder.setProducts(products);

    return purchaseOrder;
  }

  public Cart createCart() throws JsonProcessingException {
    Cart cart = createPurchaseOrder().mountCart();
    cart.setTotalPrice(BigDecimal.valueOf(35.0));

    String cartAsString = objectMapper.writeValueAsString(cart);
    redisRepository.setEx("1", 3600L, cartAsString);

    return cart;
  }

  public AppUser createUser() {
    Random random = new Random();
    int randomWithNextInt = random.nextInt();

    AppUser user = AppUser.builder()
            .name("Spring user")
            .userName("mockedUser".concat(String.valueOf(randomWithNextInt)))
            .email("email" + randomWithNextInt + "@email.com")
            .password("123")
            .build();

    return appUserRepository.save(user);
  }
  public void resetDatabase() {}
}
