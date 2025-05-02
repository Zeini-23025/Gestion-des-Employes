# Documentation des Modèles (Couche M)

Cette section décrit les entités principales de l'application de Gestion des Employés, qui représentent la couche Modèle dans l'architecture MVC.

## 1. Entité Employe

L'entité `Employe` représente un employé de l'entreprise.

```java
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private double salaire;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    
    // Getters et Setters
}
```

### Attributs
- `id` : Identifiant unique de l'employé (généré automatiquement)
- `nom` : Nom complet de l'employé
- `salaire` : Salaire de base de l'employé
- `department` : Référence vers le département auquel l'employé est rattaché (relation Many-to-One)
- `position` : Référence vers la position (poste) occupée par l'employé (relation Many-to-One)

## 2. Entité Department

L'entité `Department` représente un département de l'entreprise.

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    // Getters et Setters
}
```

### Attributs
- `id` : Identifiant unique du département (généré automatiquement)
- `name` : Nom du département (unique et non null)

## 3. Entité Position

L'entité `Position` représente un poste ou une fonction dans l'entreprise.

```java
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    // Getters et Setters
}
```

### Attributs
- `id` : Identifiant unique de la position (généré automatiquement)
- `name` : Nom de la position (unique et non null)

## Relations entre les entités

L'application présente les relations suivantes entre les entités :

1. **Relation Employe-Department** : Relation Many-to-One
   - Plusieurs employés peuvent appartenir à un même département
   - Un employé appartient à un seul département

2. **Relation Employe-Position** : Relation Many-to-One
   - Plusieurs employés peuvent avoir la même position
   - Un employé occupe une seule position

## Schéma de la base de données

```
+---------------+       +---------------+       +---------------+
|    Employe    |       |  Department   |       |   Position    |
+---------------+       +---------------+       +---------------+
| id            |       | id            |       | id            |
| nom           |       | name          |       | name          |
| salaire       |       +---------------+       +---------------+
| department_id | ----> |               |
| position_id   | ----> |               |
+---------------+       |               |
                        |               |
                        +---------------+
```

Ce schéma illustre les relations entre les trois entités principales du système.
