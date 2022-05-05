package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateAdDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseOrderDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.RedisRepository;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.model.UserRole;
import br.com.mercadolivre.projetointegrador.security.repository.AppUserRepository;
import br.com.mercadolivre.projetointegrador.security.repository.RolesRepository;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.ProductInWarehouseDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.ErrorDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.*;
import br.com.mercadolivre.projetointegrador.warehouse.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ActiveProfiles(profiles = "test")
public class IntegrationTestUtils {

  @Autowired private WarehouseRepository warehouseRepository;

  @Autowired private SectionRepository sectionRepository;

  @Autowired private ProductRepository productRepository;

  @Autowired private BatchRepository batchRepository;

  @Autowired private RolesRepository rolesRepository;

  @Autowired private RedisRepository redisRepository;

  @Autowired private AppUserRepository appUserRepository;

  private final Random random = new Random();
  @Autowired private AdRepository adRepository;

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
    Product productMock = new Product("teste" + new Random().nextInt(), CategoryEnum.FS, null);
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

  public Batch okBatch() {
    Batch batch =
        Batch.builder()
            .product(createProduct())
            .section(createSection())
            .seller(createUser())
            .price(BigDecimal.TEN)
            .order_number(123)
            .batchNumber(9595)
            .quantity(10)
            .dueDate(LocalDate.now().plusDays(10l))
            .build();
    return batchRepository.save(batch);
  }

  public Batch dueDateFiveDays() {
    Batch batch =
        Batch.builder()
            .product(createProduct())
            .section(createSection())
            .seller(createUser())
            .price(BigDecimal.TEN)
            .order_number(123)
            .batchNumber(9595)
            .quantity(10)
            .dueDate(LocalDate.now().plusDays(5l))
            .build();
    return batchRepository.save(batch);
  }

  public Batch dueDateFifteenDays() {
    Batch batch =
        Batch.builder()
            .product(createProduct())
            .section(createSection())
            .seller(createUser())
            .price(BigDecimal.TEN)
            .order_number(123)
            .batchNumber(9595)
            .quantity(10)
            .dueDate(LocalDate.now().plusDays(15l))
            .build();
    return batchRepository.save(batch);
  }

  public List<Batch> createMultipleBatchesOnSameWarehouse() {

    List<Batch> batchesToCreate = new ArrayList<>();

    Section section = createSection();
    AppUser seller = createUser();
    Product product = createProduct();

    for (int i = 0; i < 5; i++) {
      batchesToCreate.add(
          Batch.builder()
              .product(product)
              .section(section)
              .seller(seller)
              .price(BigDecimal.valueOf(10 * i + 1))
              .order_number(i)
              .batchNumber(5 - i)
              .quantity(random.nextInt(350))
              .dueDate(LocalDate.now().plusWeeks(10))
              .manufacturing_datetime(LocalDate.now())
              .build());
    }

    return batchRepository.saveAll(batchesToCreate);
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

  public Batch createBatch(Product product) {
    Batch batch =
        Batch.builder()
            .product(product)
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

  public List<UserRole> createRoles() {
    List<UserRole> roles = rolesRepository.findAll();
    if (roles.isEmpty()) {
      return rolesRepository.saveAll(
          List.of(new UserRole(null, "CUSTOMER"), new UserRole(null, "MANAGER")));
    }
    return roles;
  }

  public AppUser createUser() {
    int randomWithNextInt = random.nextInt();

    AppUser user =
        AppUser.builder()
            .name("Spring user")
            .userName("mockedUser".concat(String.valueOf(randomWithNextInt)))
            .email("email" + randomWithNextInt + "@email.com")
            .password("123")
            .build();

    return appUserRepository.save(user);
  }

  public void resetDatabase() {}

  public CreateOrUpdateAdDTO createAdDTO() {
    CreateOrUpdateAdDTO adDTO = new CreateOrUpdateAdDTO();
    adDTO.setBatchesId(List.of(1, 2, 3));
    adDTO.setName("Fake Ad");
    adDTO.setQuantity(10);
    adDTO.setPrice(BigDecimal.valueOf(10.0));
    adDTO.setDiscount(0);
    adDTO.setCategory(CategoryEnum.FS);

    return adDTO;
  }

  public List<CreatePurchaseDTO> createPurchases() {
    Ad ad = createAdDTO().DTOtoModel();
    ad.setSellerId(1L);
    adRepository.save(ad);

    CreatePurchaseDTO purchase = new CreatePurchaseDTO();
    purchase.setAdId(1L);
    purchase.setQuantity(10);
    List<CreatePurchaseDTO> purchases = List.of(purchase);

    return purchases;
  }

  public ErrorDTO createProductNotFoundError(Product product) {
    ErrorDTO errorDTO = new ErrorDTO();

    errorDTO.setError("Não encontrado");
    errorDTO.setMessage("Produto " + product.getId() + " não encontrado.");

    return errorDTO;
  }

  public ProductInWarehouse createProductInWarehouse(Product product) {
    ProductInWarehouse productInWarehouse = new ProductInWarehouse();
    Batch batch = createBatch(product);
    Warehouse warehouse = batch.getSection().getWarehouse();

    productInWarehouse.setWarehouseId(warehouse.getId());
    productInWarehouse.setProductQty(batch.getQuantity());

    return productInWarehouse;
  }

  public ProductInWarehouseDTO createProductsInWarehouse() {
    Product product = createProduct();

    List<ProductInWarehouse> productInWarehouseList = new ArrayList<>();
    productInWarehouseList.add(createProductInWarehouse(product));

    ProductInWarehouseDTO dto = new ProductInWarehouseDTO();
    dto.setProductId(product.getId());
    dto.setWarehouses(productInWarehouseList);

    return dto;
  }
}
