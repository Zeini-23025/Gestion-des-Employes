package com.zeiny.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Important si le nom est réservé
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false , unique = true) 
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;
    private boolean isActif;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActif() { return isActif; }
    public void setActif(boolean actif) { isActif = actif; }
}
