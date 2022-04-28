package br.com.mercadolivre.projetointegrador.warehouse.repository;

import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
}
