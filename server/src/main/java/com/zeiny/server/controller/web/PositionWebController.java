package com.zeiny.server.controller.web;

import com.zeiny.server.model.Position;
import com.zeiny.server.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/positions")
public class PositionWebController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public String listPositions(Model model) {
        model.addAttribute("positions", positionService.getAllPositions());
        return "positions";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("position", new Position());
        return "position-form";
    }

    @PostMapping("/save")
    public String savePosition(@ModelAttribute Position position) {
        positionService.savePosition(position);
        return "redirect:/web/positions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionService.getPositionById(id));
        return "position-form";
    }

    @GetMapping("/delete/{id}")
    public String deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return "redirect:/web/positions";
    }
}
