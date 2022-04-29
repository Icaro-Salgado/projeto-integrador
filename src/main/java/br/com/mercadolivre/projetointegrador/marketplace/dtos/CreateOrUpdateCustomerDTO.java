package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import br.com.mercadolivre.projetointegrador.marketplace.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrUpdateCustomerDTO {

  Long id;

  @NotEmpty(message = "O campo nome deve ser preenchido.")
  private String name;

  @NotEmpty(message = "O campo email deve ser preenchido.")
  @Email(message = "O email informado é inválido.")
  private String email;

  @NotEmpty(message = "O Campo de senha deve ser preenchido.")
  @Size(min = 6, max = 20, message = "A senha deve contar entre 6 e 20 caracteres.")
  private String password;

  public Customer mountCustomer() {
    Customer customer = new Customer();

    if (id != null) {
      customer.setId(id);
    }
    customer.setName(name);
    customer.setEmail(email);
    customer.setPassword(password);
    return customer;
  }
}
