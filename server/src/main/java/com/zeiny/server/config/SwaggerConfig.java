package com.zeiny.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration pour la documentation Swagger/OpenAPI
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configure les informations générales de l'API OpenAPI
     * @return L'objet OpenAPI configuré
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestion des Employés")
                        .version("1.0")
                        .description("Cette API fournit des endpoints pour gérer les employés, leurs départements et positions.")
                        .contact(new Contact()
                                .name("Zeiny")
                                .url("https://github.com/Zeini-23025/Gestion-des-Employes"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Serveur pour l'environnement de développement"),
                        new Server().url("https://gestion-employes.example.com").description("Serveur pour l'environnement de production")
                ))
                .components(new Components()
                        .addSecuritySchemes("basicAuth", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Authentification basique pour accéder aux API (actuellement désactivée)")));
    }
}