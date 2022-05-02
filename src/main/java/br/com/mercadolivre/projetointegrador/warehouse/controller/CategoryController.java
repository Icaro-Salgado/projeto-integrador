package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.response.CategoryListDTO;
import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products/categories")
public class CategoryController {

    @GetMapping
    public ResponseEntity<?> listCategories(){
        List<CategoryListDTO> categories = Arrays.stream(CategoryEnum.values()).map(val -> new CategoryListDTO(val.label(), val.name())).collect(Collectors.toList());

        return ResponseEntity.ok(categories);
    }
}
