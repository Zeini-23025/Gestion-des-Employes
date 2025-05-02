package com.zeiny.server.service;

import com.zeiny.server.repository.DepartmentRepository;
import com.zeiny.server.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public long getEmployeeCount() {
        return employeRepository.count();  // Nombre d'employés
    }

    public long getDepartmentCount() {
        return departmentRepository.count();  // Nombre de départements
    }

    public double getTotalSalary() {
        return employeRepository.sumSalary();  // Somme des salaires des employés (méthode personnalisée dans le repository)
    }
}
