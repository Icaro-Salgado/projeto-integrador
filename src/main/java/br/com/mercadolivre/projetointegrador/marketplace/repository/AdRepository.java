package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
  List<Ad> findAllBySellerId(Long id);

  @Query(value = "SELECT * FROM ad a WHERE a.name LIKE %?1%", nativeQuery = true)
  List<Ad> findAdsByLikeName(String name);
}
