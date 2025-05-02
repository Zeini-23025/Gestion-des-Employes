# Documentation de l'API - Gestion des Employés

Cette documentation détaille l'API REST du système de gestion des employés. L'API permet de gérer les employés, les départements et les positions (postes) via des requêtes HTTP.

## Table des matières

- [Accès à la documentation](#accès-à-la-documentation)
- [Authentification](#authentification)
- [Format des données](#format-des-données)
- [Endpoints API](#endpoints-api)
  - [Employés](#employés)
  - [Départements](#départements)
  - [Positions](#positions)
- [Codes de statut HTTP](#codes-de-statut-http)
- [Exemples d'utilisation](#exemples-dutilisation)

## Accès à la documentation

La documentation interactive est disponible aux URL suivantes lorsque l'application est en cours d'exécution :

- **Swagger UI** : `http://localhost:8080/swagger-ui.html`
- **Documentation OpenAPI (JSON)** : `http://localhost:8080/api-docs`
- **Guide d'utilisation de l'API** : `http://localhost:8080/api-guide.html`
- **Console H2 Database** : `http://localhost:8080/h2-console`

## Authentification

Actuellement, l'API est accessible sans authentification. Dans un environnement de production, il est recommandé d'implémenter un système d'authentification (JWT, OAuth2, etc.).

## Format des données

Toutes les requêtes et réponses sont au format JSON. Assurez-vous d'inclure l'en-tête `Content-Type: application/json` pour les requêtes POST et PUT.

## Endpoints API

### Employés

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/employes` | Récupérer tous les employés |
| GET | `/api/employes/{id}` | Récupérer un employé par ID |
| POST | `/api/employes` | Créer un nouvel employé |
| PUT | `/api/employes/{id}` | Mettre à jour un employé existant |
| DELETE | `/api/employes/{id}` | Supprimer un employé |
| GET | `/api/employes/by-department/{deptId}` | Récupérer les employés par département |
| GET | `/api/employes/by-salaire/{min}` | Récupérer les employés par salaire minimum |

**Exemple d'objet employé :**
```json
{
  "id": 1,
  "nom": "Jean Dupont",
  "salaire": 45000.0,
  "department": {
    "id": 1,
    "name": "Ressources Humaines"
  },
  "position": {
    "id": 1,
    "name": "Responsable RH"
  }
}
```

### Départements

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/departments` | Récupérer tous les départements |
| GET | `/api/departments/{id}` | Récupérer un département par ID |
| POST | `/api/departments` | Créer un nouveau département |
| PUT | `/api/departments/{id}` | Mettre à jour un département existant |
| DELETE | `/api/departments/{id}` | Supprimer un département |

**Exemple d'objet département :**
```json
{
  "id": 1,
  "name": "Ressources Humaines"
}
```

### Positions

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/positions` | Récupérer toutes les positions |
| GET | `/api/positions/{id}` | Récupérer une position par ID |
| POST | `/api/positions` | Créer une nouvelle position |
| PUT | `/api/positions/{id}` | Mettre à jour une position existante |
| DELETE | `/api/positions/{id}` | Supprimer une position |

**Exemple d'objet position :**
```json
{
  "id": 1,
  "name": "Responsable RH"
}
```

## Codes de statut HTTP

| Code | Description |
|------|-------------|
| 200 OK | La requête a réussi |
| 201 Created | Ressource créée avec succès |
| 204 No Content | La requête a réussi mais n'a pas de contenu à retourner |
| 400 Bad Request | La requête est invalide (données incorrectes) |
| 404 Not Found | La ressource demandée n'existe pas |
| 500 Internal Server Error | Erreur interne du serveur |

## Exemples d'utilisation

### Récupérer tous les employés

**Requête :**
```bash
curl -X GET "http://localhost:8080/api/employes" -H "accept: application/json"
```

### Créer un nouvel employé

**Requête :**
```bash
curl -X POST "http://localhost:8080/api/employes" \
  -H "accept: application/json" \
  -H "Content-Type: application/json" \
  -d "{ \"nom\": \"Pierre Durand\", \"salaire\": 38000.0, \"department\": { \"id\": 3 }, \"position\": { \"id\": 2 } }"
```

### Mettre à jour un employé

**Requête :**
```bash
curl -X PUT "http://localhost:8080/api/employes/3" \
  -H "accept: application/json" \
  -H "Content-Type: application/json" \
  -d "{ \"nom\": \"Pierre Durand\", \"salaire\": 42000.0, \"department\": { \"id\": 3 }, \"position\": { \"id\": 4 } }"
```

### Utilisation avec JavaScript (Fetch API)

```javascript
// Récupérer tous les employés
fetch('http://localhost:8080/api/employes')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Erreur:', error));

// Créer un nouvel employé
fetch('http://localhost:8080/api/employes', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    nom: "Sophie Leroy",
    salaire: 41000.0,
    department: {
      id: 1
    },
    position: {
      id: 2
    }
  }),
})
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Erreur:', error));
```

---

Pour plus d'informations et d'exemples, consultez la documentation interactive Swagger UI ou le guide d'utilisation de l'API lorsque l'application est en cours d'exécution.