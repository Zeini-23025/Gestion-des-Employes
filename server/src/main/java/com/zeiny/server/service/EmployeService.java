package com.zeiny.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeiny.server.model.Employe;
import com.zeiny.server.repository.EmployeRepository;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> getAll() {
        return employeRepository.findAll();
    }

    public Employe getById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    public Employe save(Employe employe) {
        return employeRepository.save(employe);
    }

    public void delete(Long id) {
        employeRepository.deleteById(id);
    }

    public List<Employe> findByDepartment(Long departmentId) {
        return employeRepository.findByDepartmentId(departmentId);
    }

    public List<Employe> findBySalaireMin(Double salaireMin) {
        return employeRepository.findBySalaireGreaterThanEqual(salaireMin);
    }
}
