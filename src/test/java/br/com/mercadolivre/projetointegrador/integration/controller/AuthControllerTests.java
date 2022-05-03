package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockCustomerUser;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockManagerUser;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.LoginDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RegisterDTO;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.security.model.UserRole;
import br.com.mercadolivre.projetointegrador.security.repository.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class AuthControllerTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private IntegrationTestUtils integrationTestUtils;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private AppUserRepository appUserRepository;

  private final String API_URL = "/api/v1/auth";
  private final String API_URL_WAREHOUSE = "/api/v1/warehouse/auth";
  private final String API_URL_MARKETPLACE = "/api/v1/marketplace/auth";

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void shouldReturn403WhenIsUnauthorized() throws Exception {
    Section section = integrationTestUtils.createSection();

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/warehouse/inboundorder/{id}", section.getId()))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void shouldRegisterNewUser() throws Exception {
    integrationTestUtils.createRoles();
    Random random = new Random();
    RegisterDTO registerDTO = new RegisterDTO(random.nextInt() + "mocked@email.com", random.nextInt() + "mocked", random.nextInt() + "mockedUser", "123");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(API_URL_WAREHOUSE + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void shouldLoginWithSuccess() throws Exception {
    AppUser user =
        new AppUser(
            null,
            "newuser@mocked.com",
            "mockedname",
            "userNamemocked",
            passwordEncoder.encode("123"));

    appUserRepository.save(user);

    LoginDTO registerDTO = new LoginDTO(user.getEmail(), "123");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().exists("Authorization"));
  }

  @Test
  public void shouldRegisterNewMarketplaceUser() throws Exception {
    integrationTestUtils.createRoles();
    RegisterDTO registerDTO = new RegisterDTO("marketplace@email.com", "mocked", "marketplacemock", "123");

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post(API_URL_MARKETPLACE + "/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registerDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    AppUser created = appUserRepository.findByEmail(registerDTO.getEmail()).orElse(new AppUser());

    Assertions.assertEquals("CUSTOMER", created.getAuthorities().stream().findFirst().orElse(new UserRole()).getName());
  }

  @Test
  @WithMockManagerUser
  public void shouldReturn403WhenAccessEndpointWithWrongRole() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders.get("/api/v1/marketplace/ads"))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockCustomerUser
  public void shouldReturn403WhenAccessWarehouseEndpointFromMarketplaceUser() throws Exception {
    mockMvc
            .perform(MockMvcRequestBuilders.get("/api/v1/warehouse/{id}", 1L))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

}
