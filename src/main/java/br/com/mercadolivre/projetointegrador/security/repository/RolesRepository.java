package br.com.mercadolivre.projetointegrador.security.repository;

import br.com.mercadolivre.projetointegrador.security.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}
