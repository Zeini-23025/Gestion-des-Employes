package com.zeiny.server.controller.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Retourne le template 404.html
        return "404";  // Le nom du template Thymeleaf
    }

    // Remove the getErrorPath method
}
