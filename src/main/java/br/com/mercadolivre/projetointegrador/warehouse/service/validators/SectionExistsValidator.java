package br.com.mercadolivre.projetointegrador.warehouse.service.validators;

import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;

import java.util.Optional;


public class SectionExistsValidator implements WarehouseValidator{

    private final Section section;
    private final SectionRepository sectionRepository;

    public SectionExistsValidator(Section s, SectionRepository r) {
        this.section = s;
        this.sectionRepository = r;
    }


    @Override
    public void Validate() {
        Optional<Section> sectionRegistered = sectionRepository.findById(section.getId());

        if (sectionRegistered.isEmpty()) throw new SectionNotFoundException("Section Not Found!");
    }
}
