package br.com.mercadolivre.projetointegrador.warehouse.repository;

import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByManager(Manager manager);
}
