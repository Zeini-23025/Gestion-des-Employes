package com.zeiny.server.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Schema(description = "Représente une position (poste) dans l'entreprise")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de la position", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Le nom de la position est obligatoire")
    @Schema(description = "Nom de la position", example = "Développeur Senior", required = true)
    private String name;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}