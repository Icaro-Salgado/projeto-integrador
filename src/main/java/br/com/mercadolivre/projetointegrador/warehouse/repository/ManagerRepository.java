package br.com.mercadolivre.projetointegrador.warehouse.repository;

import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
