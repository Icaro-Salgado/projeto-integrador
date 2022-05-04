package br.com.mercadolivre.projetointegrador.warehouse.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDTO {

  private Long id;
  private String email;
  private String name;
  private String userName;
}
