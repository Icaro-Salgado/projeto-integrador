package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.BatchDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.response.SectionBatchesDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fresh-products")
public class ProductsController {

    private final WarehouseService warehouseService;

    private final List<String> sortTypes = List.of("L", "C", "F");

    @GetMapping("list")
    public ResponseEntity<?> listStockProducts(
            @RequestParam(required = false) Long product,
            @RequestParam(required = false) SortTypeEnum sort,
            @RequestParam(required = false, name = "querytype") List<String> queries
    ) throws NotFoundException {
        // TODO: change for Authentication object from spring security
        Long managerId = 1L;

//        Long productId = Long.valueOf(queries.stream().filter(s -> s.matches("^[0-9]+$")).findFirst().orElse("-1"));

        if(product == null) {
            throw new IllegalArgumentException();
        }


//        String sortType = queries.stream().filter(sortTypes::contains).findFirst().orElse("L");

        List<Batch> batchProducts = warehouseService.findProductOnManagerSection(managerId, product, sort);

        if(batchProducts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Section section = batchProducts.get(0).getSection();

        SectionBatchesDTO response = new SectionBatchesDTO(
                section.getWarehouse().getId().toString(),
                section.getId(),
                product,
                batchProducts.stream().map(p -> new BatchDTO(p.getBatch_number(), p.getQuantity(), p.getDue_date())).collect(Collectors.toList())

        );


        return ResponseEntity.ok(response);
    }

}
