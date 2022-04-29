package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDTO {

    private Integer batchNumber;
    private Integer quantity;
    private LocalDate dueDate;
}
