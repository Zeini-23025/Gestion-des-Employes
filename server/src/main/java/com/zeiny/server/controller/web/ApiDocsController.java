package com.zeiny.server.controller.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Contrôleur pour gérer les téléchargements de documentation
 */
@Controller
@RequestMapping("/api-documentation")
public class ApiDocsController {

    /**
     * Télécharge la documentation OpenAPI au format JSON
     * @return La documentation au format JSON
     * @throws IOException Si une erreur se produit lors de la lecture du fichier
     */
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> downloadOpenApiJson() throws IOException {
        Resource resource = new ClassPathResource("static/swagger-docs.json");
        String content = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=openapi-spec.json")
                .body(content);
    }

    /**
     * Page d'accueil de la documentation
     * @return Redirection vers la page d'accueil statique
     */
    @GetMapping
    public String apiDocumentation() {
        return "redirect:/index.html";
    }

    /**
     * Raccourci vers le guide d'utilisation de l'API
     * @return Redirection vers le guide d'utilisation
     */
    @GetMapping("/guide")
    public String apiGuide() {
        return "redirect:/api-guide.html";
    }
}
