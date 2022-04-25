package br.com.mercadolivre.projetointegrador.warehouse.service;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log
public class TestService {

    @Scheduled(cron="0 0/1 * 1/1 * ?")
    public void validateSomething(){
        log.info("Executamos o metodo de validacao");
    }
}
