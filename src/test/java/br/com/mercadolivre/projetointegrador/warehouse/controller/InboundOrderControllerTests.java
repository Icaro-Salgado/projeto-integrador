package br.com.mercadolivre.projetointegrador.warehouse.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class InboundOrderControllerTests {

    @Autowired
    private MockMvc mock;

    @Test
    public void TestIfInboundOrderIsCreated() {
        assert false;
    }

    @Test
    public void TestIfInboundOrderIsUpdated() {
        assert false;
    }
}
