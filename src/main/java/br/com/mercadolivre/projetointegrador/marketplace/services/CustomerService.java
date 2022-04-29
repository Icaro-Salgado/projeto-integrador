package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UserAlreadyExistsException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Customer;
import br.com.mercadolivre.projetointegrador.marketplace.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    CustomerRepository customerRepository;

    public void createCustomer(Customer customer) throws UserAlreadyExistsException {
        Customer customerExists = findCustomerByEmail(customer.getEmail());
        if (customerExists != null) {
            throw new UserAlreadyExistsException("Usuário com o email informado já foi cadastrado.");
        }
        customerRepository.save(customer);
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    public Customer findCustomerById(Long id) throws NotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new NotFoundException("Usuário não localizado.");
        }
        return customer;
    }

    public void updateCustomer(Customer updatedCustomer) throws NotFoundException {
        findCustomerById(updatedCustomer.getId());

        customerRepository.save(updatedCustomer);
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

}
