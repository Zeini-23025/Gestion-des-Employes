# Documentation du Projet de Gestion des Employés

Bienvenue dans la documentation du projet **Gestion des Employés**. Cette documentation décrit l'architecture et le fonctionnement du système selon le pattern MCP (Model-Controller-Persistence).

## Table des matières

1. [Vue d'ensemble](#vue-densemble)
2. [Architecture MCP](#architecture-mcp)
3. [Documentation détaillée](#documentation-détaillée)
4. [API REST](#api-rest)
5. [Interface utilisateur](#interface-utilisateur)
6. [Déploiement](#déploiement)

## Vue d'ensemble

L'application de **Gestion des Employés** est une application web développée avec Spring Boot qui permet de gérer les employés d'une entreprise, leurs départements et leurs positions (postes). Elle offre à la fois une interface utilisateur basée sur Thymeleaf et une API REST pour l'intégration avec d'autres systèmes.

## Architecture MCP

L'application suit le pattern d'architecture MCP (Model-Controller-Persistence) :

- **Model (M)** : Les entités qui représentent les données métier (Employe, Department, Position)
- **Controller (C)** : Les contrôleurs qui gèrent les requêtes HTTP (API REST et Web)
- **Persistence (P)** : Les composants qui gèrent l'accès et la persistance des données (repositories et services)

```
┌─────────────┐           ┌─────────────┐           ┌─────────────┐
│             │           │             │           │             │ 
│    Model    │◄─────────►│  Controller │◄─────────►│ Persistence │
│             │           │             │           │             │
└─────────────┘           └─────────────┘           └─────────────┘
                                 ▲                        ▲
                                 │                        │
                                 ▼                        ▼
                          ┌─────────────┐           ┌─────────────┐
                          │             │           │             │
                          │   Clients   │           │  Database   │
                          │             │           │             │
                          └─────────────┘           └─────────────┘
```

## Documentation détaillée

Chaque couche du pattern MCP est documentée en détail :

- [Modèles (Couche M)](modeles.md) - Les entités et leurs relations
- [Contrôleurs (Couche C)](controleurs.md) - Les contrôleurs REST et Web
- [Persistence (Couche P)](persistence.md) - Les repositories et services

## API REST

L'application expose une API REST complète pour la gestion des employés, départements et positions. Une documentation OpenAPI/Swagger est disponible à l'adresse suivante lorsque l'application est en cours d'exécution :

- Swagger UI : `http://localhost:8080/swagger-ui.html`
- Spécifications OpenAPI : `http://localhost:8080/v3/api-docs`

La documentation complète de l'API se trouve dans le fichier [openapi.yaml](openapi.yaml).

## Interface utilisateur

L'application dispose d'une interface utilisateur basée sur Thymeleaf et Bootstrap qui permet de :

- Consulter, ajouter, modifier et supprimer des employés
- Consulter, ajouter, modifier et supprimer des départements
- Consulter, ajouter, modifier et supprimer des positions (postes)
- Rechercher des employés par département ou par salaire minimum

## Déploiement

L'application peut être déployée de trois manières différentes :

1. **Mode Développement** :
   ```bash
   git clone https://github.com/Zeini-23025/Gestion-des-Employes.git
   cd Gestion-des-Employes/server
   ./mvnw spring-boot:run
   ```

2. **Avec Docker Compose** :
   ```bash
   git clone https://github.com/Zeini-23025/Gestion-des-Employes.git
   cd Gestion-des-Employes
   docker-compose up --build
   ```

3. **Avec des images Docker préexistantes** :
   ```bash
   docker pull zeini/spring-server:latest
   docker run --name gestion-des-employe -p 8080:8080 -d zeini/spring-server:latest
   ```

Pour plus de détails sur le déploiement, veuillez consulter le [README.md](../README.md) principal du projet.
