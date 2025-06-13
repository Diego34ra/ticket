package br.edu.ifgoiano.ticket.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("URL do servidor no ambiente de desenvolvimento");

        Contact contact = new Contact();
        contact.setEmail("diegoribeiro13ra@hotmail.com");
        contact.setName("Diego Ribeiro Araújo");
        contact.setUrl("mailto:diegoribeiro13ra@hotmail.com");

        Info info = new Info()
                .title("Api Projeto Sistema de atendimento")
                .version("1.0")
                .contact(contact)
                .description("Esta API apresenta os endpoints de um projeto de gerenciar atendimentos").termsOfService("");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .servers(List.of(devServer));
    }

}