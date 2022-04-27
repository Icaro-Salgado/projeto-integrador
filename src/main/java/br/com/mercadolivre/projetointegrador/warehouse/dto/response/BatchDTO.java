package br.com.mercadolivre.projetointegrador.warehouse.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {

    private Integer batchNumber;
    private Integer quantity;
    private LocalDate dueDate;


}
