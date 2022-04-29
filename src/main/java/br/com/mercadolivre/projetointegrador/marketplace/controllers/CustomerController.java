package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreateOrUpdateCustomerDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.UserAlreadyExistsException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Customer;
import br.com.mercadolivre.projetointegrador.marketplace.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> createCustomer(
            @Valid @RequestBody CreateOrUpdateCustomerDTO createCustomerDTO,
            UriComponentsBuilder uriBuilder
    ) throws UserAlreadyExistsException {
        Customer customer = createCustomerDTO.mountCustomer();
        customerService.createCustomer(customer);

        URI uri = uriBuilder.path("/api/v1/customers/{id}").buildAndExpand(customer.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.listAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id
    ) throws NotFoundException {
        Customer customer = customerService.findCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(
            @Valid @RequestBody CreateOrUpdateCustomerDTO updateCustomerDTO,
            UriComponentsBuilder uriBuilder
    ) throws NotFoundException {
        Customer customer = updateCustomerDTO.mountCustomer();
        customerService.updateCustomer(customer);

        URI uri = uriBuilder.path("/api/v1/customers/{id}").buildAndExpand(customer.getId()).toUri();

        return ResponseEntity.noContent().location(uri).build();
    }

}
