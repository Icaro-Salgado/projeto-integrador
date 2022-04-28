package br.com.mercadolivre.projetointegrador.unit.repository;

import br.com.mercadolivre.projetointegrador.test_utils.SectionServiceTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

@DataJpaTest
@ExtendWith({SpringExtension.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = "test")
public class SectionRepositoryTests {

    @Autowired private SectionRepository sectionRepository;

    @Test
    public void shouldReturnErrorWhenReceiveInvalidTemperature() {
        Section mockedSection = SectionServiceTestUtils.getMockSection();
        mockedSection.setId(null);
        mockedSection.setMinimumTemperature(BigDecimal.valueOf(12.3303));

        Assertions.assertThrows(
                ConstraintViolationException.class, () -> sectionRepository.save(mockedSection));
    }
}
