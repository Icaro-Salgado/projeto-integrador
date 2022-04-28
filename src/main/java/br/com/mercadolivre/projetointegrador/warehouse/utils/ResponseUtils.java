package br.com.mercadolivre.projetointegrador.warehouse.utils;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import java.util.Map;
import java.util.stream.Collectors;

public class ResponseUtils {

    public static Map<String, String> parseLinksToMap(Links links) {
        return links.stream()
                .collect(Collectors.toMap((link) -> link.getRel().toString(), Link::getHref));
    }
}
