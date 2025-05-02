
# Gestion-des-Employés

Bienvenue dans le projet **Gestion des Employés** !

Cette application web a pour but de faciliter la gestion des employés au sein d'une organisation. Elle permet de gérer les employés, leurs départements, leurs postes, leurs salaires, ainsi que de fournir une interface conviviale pour l'ajout, la modification et la suppression des informations relatives aux employés. Elle utilise **Spring Boot** pour le backend, **MySQL** pour la gestion de la base de données, et **Thymeleaf** pour les pages HTML.

## 🎯 Objectif du Projet

L'objectif de ce projet est de fournir une application complète de gestion des employés qui inclut :

- La gestion des employés (ajout, modification, suppression).
- La gestion des départements et des postes.
- L'intégration d'une base de données **MySQL** pour la persistance des données.
- L'interface utilisateur réalisée avec **Thymeleaf** pour une interaction fluide avec les utilisateurs.
- L'optimisation de la gestion des ressources à travers des outils modernes tels que **Docker**, **Docker Compose**, et **GitHub Actions**.

## 🛠️ Technologies Utilisées

### Backend
- **Spring Boot** : Framework Java pour le backend.
- **Thymeleaf** : Moteur de templates pour le rendu des pages HTML côté serveur.
- **MySQL** : Système de gestion de base de données relationnelle pour stocker les données des employés, départements et postes.
  
### Frontend
- **HTML5** : Langage de balisage pour la structure des pages web.
- **CSS3** : Pour le style et la mise en page.
- **JavaScript (Vanilla)** : Pour les interactions dynamiques et l'amélioration de l'expérience utilisateur.

### Outils et Technologies DevOps
- **Docker** : Utilisé pour containeriser l'application et ses dépendances.
- **Docker Compose** : Outil pour configurer et gérer des applications multi-conteneurs.
- **Git** : Système de gestion de version pour le suivi des changements et des branches.
- **GitHub** : Hébergement de code source et gestion des versions.
- **GitHub Actions** : Automatisation des workflows CI/CD pour l'intégration et le déploiement continu.
- **Docker Hub** : Répertoire d'images Docker où l'image Docker du projet est stockée.

---

## 🚀 Méthodes pour Exécuter le Projet

### 1️⃣ Mode Développement (manuellement)
> Idéal pour contribuer au projet

#### 🔁 Lancer le projet (Spring Boot)
Pour lancer l'application en mode développement avec Spring Boot, suivez ces étapes :

```bash
git clone https://github.com/Zeini-23025/Gestion-des-Employes.git
cd Gestion-des-Employes/server
./mvnw spring-boot:run
```
Cela démarrera le serveur Spring Boot sur le port `8080`.

### 2️⃣ Avec Docker Compose
> Rapide et simple, tout tourne ensemble

Si vous préférez utiliser Docker pour exécuter le projet, voici la commande pour démarrer les services via Docker Compose :

```bash
git clone https://github.com/Zeini-23025/Gestion-des-Employes.git
cd Gestion-des-Employes
docker-compose up --build
```
Cette commande va construire et démarrer les conteneurs nécessaires (Spring Boot, MySQL, etc.).

### 3️⃣ Utilisation des images Docker (pull depuis Docker Hub)
> Pas besoin de builder, juste pull et run

Si vous préférez ne pas builder l'image Docker, vous pouvez tirer l'image préconstruite depuis Docker Hub et lancer le backend.

```bash
docker pull zeini/spring-server:latest

# Lancer le backend
docker run --name gestion-des-employe -p 8080:8080 -d zeini/spring-server:latest
```

---

## 📂 Structure du Projet

Voici un aperçu de la structure du projet :

```
# Structure du Projet

.
├── .github/
│   └── workflows/
│       └── docker.yml           # Workflow GitHub Actions pour le déploiement Docker
├── docker-compose.yml           # Fichier Docker Compose pour configurer les services (Spring Boot, MySQL, etc.)
├── README.md                    # Documentation du projet
├── Documentation/                # Documentation du projet
│   ├── api-readme.md             # Documentation des APIs
│   ├── controleurs.md            # Documentation sur les contrôleurs
│   ├── modeles.md                # Documentation des modèles
│   ├── openapi.yaml              # Spécification OpenAPI
│   ├── persistence.md            # Documentation sur la persistance des données
│   ├── README.md                 # README spécifique à la documentation
│   ├── swagger-standalone.html   # Swagger UI standalone
├── server/                       # Dossier contenant l'application Spring Boot
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── zeiny/
│   │       │           └── server/
│   │       │               ├── controller/    # Contrôleurs Web et API
│   │       │               │   ├── web/        # Contrôleurs pour les vues Thymeleaf
│   │       │               │   ├── api/        # Contrôleurs pour les APIs REST
│   │       │               ├── model/         # Entités JPA (Department, Employe, etc.)
│   │       │               ├── repository/    # Repositories JPA
│   │       │               ├── service/       # Services (Logique métier)
│   │       │               ├── config/        # Configuration Spring Boot
│   │       │               └── ServerApplication.java  # Classe principale Spring Boot
│   └── resources/
│       ├── application.properties  # Configuration Spring Boot
│       ├── static/                 # Fichiers statiques (JS, CSS)
│       └── templates/              # Templates Thymeleaf (HTML)
│           ├── 404.html           # Page d'erreur 404 personnalisée
│           ├── department-form.html  # Formulaire de gestion des départements
│           ├── departments.html      # Liste des départements
│           ├── employe-form.html    # Formulaire de gestion des employés
│           ├── employes.html        # Liste des employés
│           ├── home.html           # Page d'accueil (Dashboard)
│           ├── position-form.html   # Formulaire de gestion des postes
│           └── positions.html       # Liste des postes
└── config/                        # Fichiers de configuration
    └── SwaggerConfig.java         # Configuration Swagger pour l'API
```

### Détails des répertoires :

- **`.github/`** : Contient la configuration des workflows GitHub Actions pour le déploiement Docker.
- **`docker-compose.yml`** : Fichier de configuration Docker Compose pour démarrer l'application et ses dépendances (Spring Boot, MySQL, etc.).
- **`server/`** : Contient l'application Spring Boot avec toutes les configurations et la logique métier.
- **`Documentation/`** : Documentation complète sur l'API, les contrôleurs, la persistance, et l'intégration Swagger.
- **`static/`** et **`templates/`** : Contiennent respectivement les fichiers statiques (CSS, JS) et les templates Thymeleaf pour les pages HTML.

---

## 💬 Remerciements

Merci de contribuer à ce projet !  
Amusez-vous bien avec le projet et n'hésitez pas à proposer des améliorations ou des corrections.
