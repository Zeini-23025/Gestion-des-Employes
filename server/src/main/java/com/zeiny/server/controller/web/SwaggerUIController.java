package com.zeiny.server.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur qui redirige vers la documentation Swagger
 */
@Controller
public class SwaggerUIController {

    /**
     * Redirige la racine vers la documentation API
     * @return Le chemin de redirection vers la page de documentation Swagger
     */
    @GetMapping("/")
    public String redirectToDocumentation() {
        return "redirect:/swagger-ui.html";
    }
    
    /**
     * Affiche le guide d'utilisation de l'API
     * @return Le nom de la page HTML contenant le guide
     */
    @GetMapping("/api-guide")
    public String showApiGuide() {
        return "redirect:/api-guide.html";
    }
    
    /**
     * Redirige vers la documentation Swagger
     * @return Le chemin de redirection vers Swagger UI
     */
    @GetMapping("/api-doc")
    public String showSwaggerUI() {
        return "redirect:/swagger-ui.html";
    }
}
