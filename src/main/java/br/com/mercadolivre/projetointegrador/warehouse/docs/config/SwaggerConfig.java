package br.com.mercadolivre.projetointegrador.warehouse.docs.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI meliFreshOpenAPI() {

    return new OpenAPI()
        .info(new Info()
                .title("MercadoFresh - Projeto Integrador")
                .description("MercadoFresh API - Projeto Integrador Bootcamp Mercado Libre - Grupo(Javali) - Wave 5")
                .version("v1.0.0")
                .license(new License().name("Mercado Livre").url("https:www.mercadolibre.com"))
                .contact(
                    new Contact()
                        .name("Javali Developers")
                        .url("https://github.com/Icaro-Salgado/projeto-integrador")
                        .email("evandro.sutil@mercadolivre.com;" +
                                "icaro.salgado@mercadolivre.com;" +
                                "klinton.lee@mercadolivre.com;" +
                                "maran.morimoto@mercadolivre.com;" +
                                "paulo.alima@mercadolivre.com;" +
                                "pedro.levada@mercadolivre.com;" +
                                "thainan.santos@mercadolivre.com"))
        ).externalDocs(
            new ExternalDocumentation()
                .description("GitHub")
                .url("https://github.com/Icaro-Salgado/projeto-integrador"));
  }

//  @Bean
//  public OpenApiCustomiser CustomerGlobalHeaderOpenAPICustomiser() {
//    return openApi -> {
//      openApi
//          .getPaths()
//          .values()
//          .forEach(
//              pathItem ->
//                  pathItem
//                      .readOperations()
//                      .forEach(
//                          operation -> {
//                            ApiResponses apiResponses = operation.getResponses();
//
//                            apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
//                            apiResponses.addApiResponse("201", createApiResponse("Objeto criado!"));
//                            apiResponses.addApiResponse(
//                                "204", createApiResponse("Objeto excluido!"));
//                            apiResponses.addApiResponse(
//                                "400", createApiResponse("Erro na requisição!"));
//                            apiResponses.addApiResponse(
//                                "404", createApiResponse("Objeto não encontrado!"));
//                          }));
//    };
//  }

//  private ApiResponse createApiResponse(String message) {
//    return new ApiResponse().description(message);
//  }
}
