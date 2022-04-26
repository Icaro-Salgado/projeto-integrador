package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    public Manager findById(Long id) throws RuntimeException{
        return managerRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
