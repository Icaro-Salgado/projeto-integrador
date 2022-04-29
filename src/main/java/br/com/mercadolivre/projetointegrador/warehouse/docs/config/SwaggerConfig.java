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

public class SwaggerConfig {
    @Bean
    public OpenAPI meliFreshOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto Integrador")
                        .description("projeto MeliFresh - mercado livre")
                        .version("v0.0.1")
                        .license(new License()
                                .name("mercado livre")
                                .url("mercadolivre.com.br"))
                        .contact(new Contact()
                                .name("Javali")
                                .url("https://github.com/Icaro-Salgado/projeto-integrador")
                                .email("javali@mercadolivre.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("github")
                        .url("https://github.com/Icaro-Salgado/projeto-integrador"));

    }

    @Bean
    public OpenApiCustomiser CustomerGlobalHeaderOpenAPICustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

                ApiResponses apiResponses = operation.getResponses();

                apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
                apiResponses.addApiResponse("201", createApiResponse("Objeto criado!"));
                apiResponses.addApiResponse("204", createApiResponse("Objeto excluido!"));
                apiResponses.addApiResponse("400", createApiResponse("Erro na requisição!"));
                apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado!"));
            }));
        };
    }


    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}
