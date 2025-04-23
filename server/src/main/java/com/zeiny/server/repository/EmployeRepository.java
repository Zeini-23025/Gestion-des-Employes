package com.zeiny.server.repository;

import com.zeiny.server.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByDepartmentId(Long departmentId);
    List<Employe> findBySalaireGreaterThanEqual(Double salaire);
}
