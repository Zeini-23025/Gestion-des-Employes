package com.zeiny.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zeiny.server.model.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByDepartmentId(Long departmentId);
    List<Employe> findBySalaireGreaterThanEqual(Double salaire);

    public List<Employe> findByDepartmentIdAndSalaireGreaterThanEqual(Long departmentId, Double minSalaire);
    List<Employe> findTop5ByOrderByIdDesc();
    @Query("SELECT SUM(e.salaire) FROM Employe e")
    double sumSalary();

}
