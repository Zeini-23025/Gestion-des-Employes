package com.zeiny.server.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zeiny.server.model.Employe;
import com.zeiny.server.service.EmployeService;
import com.zeiny.server.service.HomeService;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private EmployeService employeService;

    @GetMapping("/web/home")
    public String home(Model model) {
        // Statistiques à afficher sur la page d'accueil
        long employeeCount = homeService.getEmployeeCount(); // Nombre d'employés
        long departmentCount = homeService.getDepartmentCount(); // Nombre de départements
        double totalSalary = homeService.getTotalSalary(); // Salaire total des employés

        // Derniers 5 employés ajoutés
        List<Employe> last5Employees = employeService.getLast5Employees();

        // Ajouter les données au modèle
        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("departmentCount", departmentCount);
        model.addAttribute("totalSalary", totalSalary);
        model.addAttribute("last5Employees", last5Employees);

        // Retourner la vue Thymeleaf
        return "home";
    }
}
