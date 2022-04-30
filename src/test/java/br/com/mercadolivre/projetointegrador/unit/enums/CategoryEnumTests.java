package br.com.mercadolivre.projetointegrador.unit.enums;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryEnumTests {

    CategoryEnum categoryEnum;

    @Test
    public void validateFS() {
        assertTrue(CategoryEnum.contains("FS"));
    }

    @Test
    public void validateRF() {
        assertTrue(CategoryEnum.contains("RF"));
    }

    @Test
    public void validateFF() {
        assertTrue(CategoryEnum.contains("FF"));
    }

    @Test
    public void invalidEnum() {
        assertFalse(CategoryEnum.contains("GG"));
    }
}