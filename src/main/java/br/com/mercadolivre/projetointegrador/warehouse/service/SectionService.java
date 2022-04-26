package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    public Section findSectionById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException("Section not found")); //TODO: replace exception
    }

    public Section findSectionByManager(Manager manager){
        return sectionRepository.findByManager(manager).orElseThrow(() -> new PropertyNotFoundException("Section not found"));
    }

}
