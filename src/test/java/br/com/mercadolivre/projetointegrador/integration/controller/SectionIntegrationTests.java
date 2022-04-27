package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.SectionServiceTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SectionIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IntegrationTestUtils integrationTestUtils;

    private final String SECTION_URL = "/api/v1/section";



    @Test
    public void shouldReturnObjectContainingSection() throws Exception {
        Section section = integrationTestUtils.createSection();

        mockMvc.perform(MockMvcRequestBuilders.
                get(SECTION_URL.concat("/{id}"), section.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
