package br.com.mercadolivre.projetointegrador.warehouse.docs.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.stream.Collectors;

@Configuration
@SecurityScheme(
        name = "WarehouseAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@SecurityScheme(
        name = "MarketplaceAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

  @Bean
  public OpenApiCustomiser sortTagsAlphabetically() {
    return openApi -> openApi.setTags(openApi.getTags()
            .stream()
            .sorted(Comparator.comparing(tag -> StringUtils.stripAccents(tag.getName())))
            .collect(Collectors.toList()));
  }

  @Bean
  public OpenAPI meliFreshOpenAPI() {

    return new OpenAPI()
        .info(
            new Info()
                .title("MercadoFresh - Projeto Integrador")
                .description(
                    "MercadoFresh API - Projeto Integrador Bootcamp Mercado Libre - Grupo(Javali) -"
                        + " Wave 5")
                .version("v1.0.0")
                .license(new License().name("Mercado Livre").url("https:www.mercadolibre.com"))
                .contact(
                    new Contact()
                        .name("Javali Developers")
                        .url("https://github.com/Icaro-Salgado/projeto-integrador")
                        .email(
                            "evandro.sutil@mercadolivre.com;"
                                + "icaro.salgado@mercadolivre.com;"
                                + "klinton.lee@mercadolivre.com;"
                                + "maran.morimoto@mercadolivre.com;"
                                + "paulo.alima@mercadolivre.com;"
                                + "pedro.levada@mercadolivre.com;"
                                + "thainan.santos@mercadolivre.com")))
        .externalDocs(
            new ExternalDocumentation()
                .description("GitHub")
                .url("https://github.com/Icaro-Salgado/projeto-integrador"));
  }
}
