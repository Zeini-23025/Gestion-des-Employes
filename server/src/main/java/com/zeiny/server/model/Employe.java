package com.zeiny.server.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Schema(description = "Représente un employé dans le système")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de l'employé", example = "1")
    private Long id;

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    @Schema(description = "Nom complet de l'employé", example = "Jean Dupont", required = true)
    private String nom;
    
    @NotNull(message = "Le salaire de l'employé est obligatoire")
    @Min(value = 0, message = "Le salaire doit être positif")
    @Schema(description = "Salaire de base de l'employé", example = "45000.00", required = true)
    private double salaire;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @Schema(description = "Département auquel l'employé appartient")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @Schema(description = "Position (poste) occupée par l'employé")
    private Position position;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}