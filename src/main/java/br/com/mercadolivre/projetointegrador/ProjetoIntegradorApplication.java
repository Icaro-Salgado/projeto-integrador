package br.com.mercadolivre.projetointegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjetoIntegradorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoIntegradorApplication.class, args);
    }
}
