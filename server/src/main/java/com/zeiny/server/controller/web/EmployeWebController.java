package com.zeiny.server.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zeiny.server.model.Employe;
import com.zeiny.server.service.DepartmentService;
import com.zeiny.server.service.EmployeService;
import com.zeiny.server.service.PositionService;

@Controller
@RequestMapping("/web/employes")
public class EmployeWebController {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employes", employeService.getAll());
        return "employes";
    }

    @GetMapping("/new")
    public String newEmploye(Model model) {
        model.addAttribute("employe", new Employe());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "employe-form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("employe", employeService.getById(id));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "employe-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employe employe) {
        employeService.save(employe);
        return "redirect:/web/employes"; // 🔧 Corrigé ici
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeService.delete(id);
        return "redirect:/web/employes"; // 🔧 Corrigé ici
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Long departmentId,
                         @RequestParam(required = false) Double salaire,
                         Model model) {
        List<Employe> results;
        if (departmentId != null) {
            results = employeService.findByDepartment(departmentId);
        } else if (salaire != null) {
            results = employeService.findBySalaireMin(salaire);
        } else {
            results = employeService.getAll();
        }
        model.addAttribute("employes", results);
        return "employes";
    }
}
