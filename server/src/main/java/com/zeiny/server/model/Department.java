package com.zeiny.server.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
@Schema(description = "Représente un département dans l'entreprise")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique du département", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Le nom du département est obligatoire")
    @Schema(description = "Nom du département", example = "Ressources Humaines", required = true)
    private String name;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}