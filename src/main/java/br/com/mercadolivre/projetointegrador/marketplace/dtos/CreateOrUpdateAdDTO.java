package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrUpdateAdDTO {

  private Long id;

  @NotEmpty(message = "O produto deve pertencer a pelo menos um lote.")
  private List<Long> batches;

  // O Seller ID chegará pelo Headers via token JWT
  private Long seller_id;

  @NotEmpty(message = "O campo nome deve ser preenchido.")
  private String name;

  @Min(value = 1, message = "Deve conter ao menos um produto para ser vendido.")
  private int quantity;

  private BigDecimal price;
  private int discount;
  private String category;
  private LocalDate manufacturing_date;
  private LocalDate due_date;
}
