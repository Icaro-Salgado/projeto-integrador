package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateAdDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockCustomerUser;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockCustomerUser
public class AdControllerTests {
  private final String AD_URL = "/api/v1/marketplace/ads";
  @Autowired private MockMvc mockMvc;
  @Autowired private AdRepository adRepository;
  @Autowired private IntegrationTestUtils integrationTestUtils;
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("AdController - POST - api/v1/ads")
  public void testCreateAd() throws Exception {

    CreateOrUpdateAdDTO adDTO = integrationTestUtils.createAdDTO();

    mockMvc
      .perform(
        MockMvcRequestBuilders.post(AD_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(adDTO)))
      .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @DisplayName("AdController - GET - api/v1/ads/{adId}")
  public void testFindAd() throws Exception {
    CreateOrUpdateAdDTO adDTO = integrationTestUtils.createAdDTO();

    Ad ad = adDTO.DTOtoModel();
    ad.setSellerId(1L);

    adRepository.save(ad);

    mockMvc
      .perform(
        MockMvcRequestBuilders.get(AD_URL + "/" + ad.getId())
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fake Ad"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10))
      .andExpect(MockMvcResultMatchers.jsonPath("$.discount").value(0))
      .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("FS"));
  }

  @Test
  @DisplayName("AdController - GET - api/v1/ads")
  public void testListAds() throws Exception {
    Ad ad1 = integrationTestUtils.createAdDTO().DTOtoModel();
    Ad ad2 = integrationTestUtils.createAdDTO().DTOtoModel();

    ad1.setSellerId(1L);
    ad2.setName("Fake Ad 2");
    ad2.setSellerId(2L);

    adRepository.save(ad1);
    adRepository.save(ad2);

    mockMvc
      .perform(
        MockMvcRequestBuilders.get(AD_URL)
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].sellerId").value("1"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Fake Ad"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].sellerId").value("2"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Fake Ad 2"));
  }

  @Test
  @DisplayName("AdController - GET - api/v1/ads?name={ad name}")
  public void testListAdsQueryingName() throws Exception {
    Ad ad1 = integrationTestUtils.createAdDTO().DTOtoModel();
    Ad ad2 = integrationTestUtils.createAdDTO().DTOtoModel();

    ad1.setSellerId(1L);
    ad2.setName("Fake Ad 2");
    ad2.setSellerId(2L);

    adRepository.save(ad1);
    adRepository.save(ad2);

    mockMvc
      .perform(
        MockMvcRequestBuilders.get(AD_URL + "?name=Fake%Ad%2")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].sellerId").value("2"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Fake Ad 2"));
  }

  @Test
  @DisplayName("AdController - GET - api/v1/ads?category={category}")
  public void testListAdsQueryingCategory() throws Exception {
    Ad ad1 = integrationTestUtils.createAdDTO().DTOtoModel();
    Ad ad2 = integrationTestUtils.createAdDTO().DTOtoModel();

    ad2.setName("Frozen Product Ad");
    ad2.setCategory(CategoryEnum.FF);

    adRepository.save(ad1);
    adRepository.save(ad2);

    mockMvc
      .perform(
        MockMvcRequestBuilders.get(AD_URL + "?category=FF")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Frozen Product Ad"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("FF"));
  }

  @Test
  @DisplayName("AdController - GET - api/v1/ads/seller")
  public void testListCustomerAds() throws Exception {
    Ad ad1 = integrationTestUtils.createAdDTO().DTOtoModel();
    ad1.setSellerId(1L);
    Ad ad2 = integrationTestUtils.createAdDTO().DTOtoModel();
    ad2.setName("Fake Ad 2");
    ad2.setSellerId(1L);

    adRepository.save(ad1);
    adRepository.save(ad2);

    mockMvc
      .perform(
        MockMvcRequestBuilders.get(AD_URL + "/seller"))
      .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Fake Ad"))
      .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Fake Ad 2"));
  }

  @Test
  @DisplayName("AdController - DELETE - api/v1/ads/{adId}/delete")
  public void testDeleteAd() throws Exception {
    Ad ad = integrationTestUtils.createAdDTO().DTOtoModel();
    ad.setSellerId(1L);
    adRepository.save(ad);

    System.out.println(AD_URL + "/" + ad.getId() + "/delete");
    mockMvc
      .perform(
        MockMvcRequestBuilders.delete(AD_URL + "/" + ad.getId() + "/delete"))
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}


