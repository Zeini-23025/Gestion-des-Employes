package com.zeiny.server.employe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Employe {

    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 50)
    private String nom;
    @Column(length = 50)
    private String poste;
    @Column(length = 8)
    private int salaire;
    @Column(length = 50)
    private String departement;

    public Employe(){
        
    }
    public Employe(String departement, String nom, String poste, int salaire) {
        this.departement = departement;
        this.nom = nom;
        this.poste = poste;
        this.salaire = salaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

}
