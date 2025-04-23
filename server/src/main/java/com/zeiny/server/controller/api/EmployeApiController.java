package com.zeiny.server.controller.api;

import com.zeiny.server.model.Employe;
import com.zeiny.server.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeApiController {

    @Autowired
    private EmployeService employeService;

    @GetMapping
    public List<Employe> getAll() {
        return employeService.getAll();
    }

    @GetMapping("/{id}")
    public Employe getById(@PathVariable Long id) {
        return employeService.getById(id);
    }

    @PostMapping
    public Employe create(@RequestBody Employe employe) {
        return employeService.save(employe);
    }

    @PutMapping("/{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe updated) {
        updated.setId(id);
        return employeService.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeService.delete(id);
    }

    @GetMapping("/by-department/{deptId}")
    public List<Employe> byDepartment(@PathVariable Long deptId) {
        return employeService.findByDepartment(deptId);
    }

    @GetMapping("/by-salaire/{min}")
    public List<Employe> bySalaire(@PathVariable Double min) {
        return employeService.findBySalaireMin(min);
    }
}
