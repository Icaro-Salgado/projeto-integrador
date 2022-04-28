package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateSectionPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    private final WarehouseService warehouseService;

    public Section findSectionById(Long id) {
        return sectionRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new PropertyNotFoundException(
                                        "Section not found")); // TODO: replace exception
    }

    public Section createSection(CreateSectionPayloadDTO payload) {
        Warehouse warehouse = warehouseService.findWarehouse(payload.getWarehouseId());

        Section newSection =
                Section.builder()
                        .warehouse(warehouse)
                        .manager(payload.getManagerId().toString())
                        .minimumTemperature(payload.getMinimumTemperature())
                        .maximumTemperature(payload.getMaximumTemperature())
                        .product_category(payload.getProductCategory())
                        .capacity(payload.getCapacity())
                        .build();

        return sectionRepository.save(newSection);
    }
}
