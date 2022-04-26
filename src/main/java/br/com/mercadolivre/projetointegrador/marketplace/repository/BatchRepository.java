package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {

<<<<<<< Updated upstream
    List<Batch> findBatchByProduct(Product product);
=======
    public List<Batch> findAllBySection_id(Long id);
>>>>>>> Stashed changes
}
