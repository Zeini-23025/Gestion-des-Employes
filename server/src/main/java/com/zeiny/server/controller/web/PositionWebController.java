package com.zeiny.server.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zeiny.server.model.Position;
import com.zeiny.server.service.PositionService;

@Controller
@RequestMapping("/web/positions")
public class PositionWebController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public String listPositions(Model model) {
        model.addAttribute("positions", positionService.getAllPositions());
        return "positions"; // Affiche la liste des postes
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("position", new Position());
        return "position-form"; // Formulaire pour ajouter un poste
    }

    @PostMapping("/save")
    public String savePosition(@ModelAttribute Position position) {
        positionService.savePosition(position);
        return "redirect:/web/positions"; // Redirige vers la liste des postes après l'enregistrement
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionService.getPositionById(id));
        return "position-form"; // Formulaire pour modifier un poste
    }

    @GetMapping("/delete/{id}")
    public String deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return "redirect:/web/positions"; // Redirige vers la liste des postes après la suppression
    }
}
