# Documentation de la Persistence (Couche P)

Cette section décrit la couche Persistence de l'application de Gestion des Employés, qui comprend les repositories et les services qui interagissent avec la base de données.

## 1. Repositories

Les repositories étendent JpaRepository et fournissent des méthodes pour interagir avec la base de données.

### 1.1 EmployeRepository

```java
@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    /**
     * Trouve tous les employés appartenant à un département spécifique
     * @param departmentId L'ID du département
     * @return Une liste d'employés
     */
    List<Employe> findByDepartmentId(Long departmentId);
    
    /**
     * Trouve tous les employés ayant un salaire supérieur ou égal à une valeur minimale
     * @param salaire Le salaire minimum
     * @return Une liste d'employés
     */
    List<Employe> findBySalaireGreaterThanEqual(Double salaire);
    
    /**
     * Trouve tous les employés appartenant à un département spécifique et ayant un salaire minimum
     * @param departmentId L'ID du département
     * @param minSalaire Le salaire minimum
     * @return Une liste d'employés
     */
    List<Employe> findByDepartmentIdAndSalaireGreaterThanEqual(Long departmentId, Double minSalaire);
}
```

### 1.2 DepartmentRepository

```java
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Utilise les méthodes héritées de JpaRepository
}
```

### 1.3 PositionRepository

```java
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    // Utilise les méthodes héritées de JpaRepository
}
```

## 2. Services

Les services encapsulent la logique métier et font le lien entre les contrôleurs et les repositories.

### 2.1 EmployeService

```java
@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;
    
    /**
     * Récupère tous les employés
     * @return Liste de tous les employés
     */
    public List<Employe> getAll() {
        return employeRepository.findAll();
    }
    
    /**
     * Récupère un employé par son ID
     * @param id L'ID de l'employé
     * @return L'employé trouvé ou null
     */
    public Employe getById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }
    
    /**
     * Sauvegarde un employé (création ou mise à jour)
     * @param employe L'employé à sauvegarder
     * @return L'employé sauvegardé
     */
    public Employe save(Employe employe) {
        return employeRepository.save(employe);
    }
    
    /**
     * Supprime un employé par son ID
     * @param id L'ID de l'employé à supprimer
     */
    public void delete(Long id) {
        employeRepository.deleteById(id);
    }
    
    /**
     * Trouve les employés par département
     * @param departmentId L'ID du département
     * @return Liste des employés du département
     */
    public List<Employe> findByDepartment(Long departmentId) {
        return employeRepository.findByDepartmentId(departmentId);
    }
    
    /**
     * Trouve les employés avec un salaire minimum
     * @param salaireMin Le salaire minimum
     * @return Liste des employés avec un salaire >= salaireMin
     */
    public List<Employe> findBySalaireMin(Double salaireMin) {
        return employeRepository.findBySalaireGreaterThanEqual(salaireMin);
    }
    
    /**
     * Calcule la taxe pour un employé (10% du salaire)
     * @param employe L'employé
     * @return Le montant de la taxe
     */
    public double calculateTax(Employe employe) {
        return employe.getSalaire() * 0.10;
    }
    
    /**
     * Calcule le bonus pour un employé (15% du salaire)
     * @param employe L'employé
     * @return Le montant du bonus
     */
    public double calculateBonus(Employe employe) {
        return employe.getSalaire() * 0.15;
    }
    
    /**
     * Calcule le salaire avec taxe déduite
     * @param employe L'employé
     * @return Le salaire après déduction de la taxe
     */
    public double calculateTotalWithTax(Employe employe) {
        return employe.getSalaire() - calculateTax(employe);
    }
    
    /**
     * Calcule le salaire avec bonus ajouté
     * @param employe L'employé
     * @return Le salaire après ajout du bonus
     */
    public double calculateTotalWithBonus(Employe employe) {
        return employe.getSalaire() + calculateBonus(employe);
    }
    
    /**
     * Calcule le salaire final (salaire - taxe + bonus)
     * @param employe L'employé
     * @return Le salaire final
     */
    public double calculateFinalSalary(Employe employe) {
        return employe.getSalaire() - calculateTax(employe) + calculateBonus(employe);
    }
}
```

### 2.2 DepartmentService

```java
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    
    /**
     * Récupère tous les départements
     * @return Liste de tous les départements
     */
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    /**
     * Récupère un département par son ID
     * @param id L'ID du département
     * @return Le département trouvé ou null
     */
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }
    
    /**
     * Sauvegarde un département (création ou mise à jour)
     * @param department Le département à sauvegarder
     * @return Le département sauvegardé
     */
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
    
    /**
     * Supprime un département par son ID
     * @param id L'ID du département à supprimer
     */
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
```

### 2.3 PositionService

```java
@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;
    
    /**
     * Récupère toutes les positions
     * @return Liste de toutes les positions
     */
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
    
    /**
     * Récupère une position par son ID
     * @param id L'ID de la position
     * @return La position trouvée ou null
     */
    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElse(null);
    }
    
    /**
     * Sauvegarde une position (création ou mise à jour)
     * @param position La position à sauvegarder
     * @return La position sauvegardée
     */
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }
    
    /**
     * Supprime une position par son ID
     * @param id L'ID de la position à supprimer
     */
    public void deletePosition(Long id) {
        positionRepository.deleteById(id);
    }
}
```

## 3. Configuration de la Persistence

### 3.1 Configuration JPA et Datasource

La configuration de la persistence est définie dans le fichier `application.properties` :

```properties
# Configuration de la base de données
spring.datasource.url=jdbc:mysql://localhost:3306/employe_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=23025
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuration JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3.2 Explication des paramètres

- `spring.datasource.url` : URL de connexion à la base de données MySQL
- `spring.datasource.username` et `spring.datasource.password` : Identifiants de connexion à la base de données
- `spring.datasource.driver-class-name` : Pilote JDBC pour MySQL
- `spring.jpa.hibernate.ddl-auto=update` : Configuration pour mettre à jour automatiquement le schéma de la base de données
- `spring.jpa.show-sql=true` : Affiche les requêtes SQL générées par JPA
- `spring.jpa.properties.hibernate.dialect` : Dialecte SQL spécifique à MySQL 8

### 3.3 Configuration pour l'environnement Docker

Dans l'environnement Docker, les paramètres de connexion à la base de données sont remplacés par des variables d'environnement définies dans le fichier `docker-compose.yml` :

```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/employe_db
  - SPRING_DATASOURCE_USERNAME=root
  - SPRING_DATASOURCE_PASSWORD=root
  - SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver
```

## 4. Schéma de la Persistence (JPA)

```
┌───────────────────┐         ┌───────────────────┐
│  EmployeService   │         │ EmployeRepository │
└───────────────────┘         └───────────────────┘
          │                              │
          │                              │
          ▼                              ▼
┌───────────────────┐         ┌───────────────────┐
│  DepartmentService│         │DepartmentRepository
└───────────────────┘         └───────────────────┘
          │                              │
          │                              │
          ▼                              ▼
┌───────────────────┐         ┌───────────────────┐
│  PositionService  │         │ PositionRepository│
└───────────────────┘         └───────────────────┘
          │                              │
          │                              │
          └──────────────────────────────┘
                         │
                         ▼
                  ┌─────────────┐
                  │  Database   │
                  │   (MySQL)   │
                  └─────────────┘
```

Ce schéma illustre comment les services interagissent avec les repositories, qui à leur tour communiquent avec la base de données.
