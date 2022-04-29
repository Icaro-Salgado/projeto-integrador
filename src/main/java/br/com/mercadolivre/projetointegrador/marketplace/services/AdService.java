package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdService {

    AdRepository adRepository;

    public void createAd(Ad ad) {
        this.adRepository.save(ad);
    }

    public List<Ad> listAdsByCustomerId(Long id) {
         return this.adRepository.findAllBySellerId(id);
    }

    public Ad findAdById(Long id) {
        Ad ad = this.adRepository.findById(id).orElse(null);
        if (ad == null) {
            throw new NotFoundException("Anúncio não localizado.");
        }
        return ad;
    }

    public void updateAd(Ad updatedAd) {
        findAdById(updatedAd.getId());
        adRepository.save(updatedAd);
    }

    public void deleteAd(Long id) throws NotFoundException {
        Ad ad = findAdById(id);
        adRepository.delete(ad);
    }
}
