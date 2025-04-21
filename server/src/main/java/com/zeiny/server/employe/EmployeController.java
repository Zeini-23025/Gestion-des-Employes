package com.zeiny.server.employe;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeController {
    private final EmployeRepository employeRepository;

    public EmployeController(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }
    @PostMapping("/employe/ajoute")
    @ResponseStatus(HttpStatus.CREATED)
    public Employe ajouteEmploye(
        @RequestBody Employe employe
    ){
        return employeRepository.save(employe);
    }
    @GetMapping("/employe")
    public List<Employe> afficheEmploye(){
        return employeRepository.findAll();
    }
    @GetMapping("/employe/{employe-id}")
    public Employe trouverRmployeById(
        @PathVariable("employe-id") Long id
    ){
        return employeRepository.findById(id).orElse(null);
    }
    @DeleteMapping("/employe/supprime/{student-id}")
    public void supprimerEmploye(
        @PathVariable("student-id") Long id
    ){
        employeRepository.deleteById(id);
    }
}
