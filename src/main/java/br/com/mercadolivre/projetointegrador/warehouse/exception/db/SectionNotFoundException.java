package br.com.mercadolivre.projetointegrador.warehouse.exception.db;

public class SectionNotFoundException extends RuntimeException {

    public SectionNotFoundException(String msg) {
        super(msg);
    }
}
