# Documentation des Contrôleurs (Couche C)

Cette section décrit les contrôleurs de l'application de Gestion des Employés, qui représentent la couche Contrôleur dans l'architecture MVC.

L'application dispose de deux types de contrôleurs :
- **Contrôleurs API** : pour les interactions programmatiques via des endpoints REST
- **Contrôleurs Web** : pour les interactions utilisateur via des vues Thymeleaf

## 1. Contrôleurs API REST

### 1.1 EmployeApiController

Ce contrôleur gère les opérations CRUD et les recherches pour les employés via des endpoints REST.

```java
@RestController
@RequestMapping("/api/employes")
@Tag(name = "Employés", description = "API de gestion des employés")
public class EmployeApiController {
    @Autowired
    private EmployeService employeService;
    
    @GetMapping
    @Operation(summary = "Liste tous les employés", description = "Récupère la liste complète des employés avec leurs informations")
    public List<Employe> getAll() {
        return employeService.getAll();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtient un employé par ID", description = "Récupère les détails d'un employé spécifique par son identifiant")
    public Employe getById(@PathVariable Long id) {
        return employeService.getById(id);
    }
    
    @PostMapping
    @Operation(summary = "Crée un nouvel employé", description = "Ajoute un nouvel employé dans la base de données")
    public Employe create(@RequestBody Employe employe) {
        return employeService.save(employe);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un employé", description = "Met à jour les informations d'un employé existant")
    public Employe update(@PathVariable Long id, @RequestBody Employe updated) {
        updated.setId(id);
        return employeService.save(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un employé", description = "Supprime un employé de la base de données")
    public void delete(@PathVariable Long id) {
        employeService.delete(id);
    }
    
    @GetMapping("/by-department/{deptId}")
    @Operation(summary = "Liste les employés par département", description = "Récupère la liste des employés appartenant à un département spécifique")
    public List<Employe> byDepartment(@PathVariable Long deptId) {
        return employeService.findByDepartment(deptId);
    }
    
    @GetMapping("/by-salaire/{min}")
    @Operation(summary = "Liste les employés par salaire minimum", description = "Récupère la liste des employés ayant un salaire supérieur ou égal au minimum spécifié")
    public List<Employe> bySalaire(@PathVariable Double min) {
        return employeService.findBySalaireMin(min);
    }
}
```

#### Endpoints disponibles

| Méthode | URL                           | Description                                           |
|---------|-------------------------------|-------------------------------------------------------|
| GET     | /api/employes                 | Récupère tous les employés                            |
| GET     | /api/employes/{id}            | Récupère un employé par son ID                        |
| POST    | /api/employes                 | Crée un nouvel employé                                |
| PUT     | /api/employes/{id}            | Met à jour un employé existant                        |
| DELETE  | /api/employes/{id}            | Supprime un employé                                   |
| GET     | /api/employes/by-department/{deptId} | Récupère les employés par département         |
| GET     | /api/employes/by-salaire/{min}      | Récupère les employés avec salaire minimum     |

### 1.2 DepartmentApiController

Ce contrôleur gère les opérations CRUD pour les départements via des endpoints REST.

```java
@RestController
@RequestMapping("/api/departments")
@Tag(name = "Départements", description = "API de gestion des départements")
public class DepartmentApiController {
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping
    @Operation(summary = "Liste tous les départements", description = "Récupère la liste complète des départements")
    public List<Department> getAll() {
        return departmentService.getAllDepartments();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtient un département par ID", description = "Récupère les détails d'un département spécifique par son identifiant")
    public Department getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }
    
    @PostMapping
    @Operation(summary = "Crée un nouveau département", description = "Ajoute un nouveau département dans la base de données")
    public Department create(@RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un département", description = "Met à jour les informations d'un département existant")
    public Department update(@PathVariable Long id, @RequestBody Department updated) {
        updated.setId(id);
        return departmentService.saveDepartment(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un département", description = "Supprime un département de la base de données")
    public void delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
```

#### Endpoints disponibles

| Méthode | URL                      | Description                                  |
|---------|--------------------------|----------------------------------------------|
| GET     | /api/departments         | Récupère tous les départements               |
| GET     | /api/departments/{id}    | Récupère un département par son ID           |
| POST    | /api/departments         | Crée un nouveau département                  |
| PUT     | /api/departments/{id}    | Met à jour un département existant           |
| DELETE  | /api/departments/{id}    | Supprime un département                      |

### 1.3 PositionApiController

Ce contrôleur gère les opérations CRUD pour les positions (postes) via des endpoints REST.

```java
@RestController
@RequestMapping("/api/positions")
@Tag(name = "Positions", description = "API de gestion des positions (postes)")
public class PositionApiController {
    @Autowired
    private PositionService positionService;
    
    @GetMapping
    @Operation(summary = "Liste toutes les positions", description = "Récupère la liste complète des positions")
    public List<Position> getAll() {
        return positionService.getAllPositions();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtient une position par ID", description = "Récupère les détails d'une position spécifique par son identifiant")
    public Position getById(@PathVariable Long id) {
        return positionService.getPositionById(id);
    }
    
    @PostMapping
    @Operation(summary = "Crée une nouvelle position", description = "Ajoute une nouvelle position dans la base de données")
    public Position create(@RequestBody Position position) {
        return positionService.savePosition(position);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Met à jour une position", description = "Met à jour les informations d'une position existante")
    public Position update(@PathVariable Long id, @RequestBody Position updated) {
        updated.setId(id);
        return positionService.savePosition(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime une position", description = "Supprime une position de la base de données")
    public void delete(@PathVariable Long id) {
        positionService.deletePosition(id);
    }
}
```

#### Endpoints disponibles

| Méthode | URL                    | Description                                |
|---------|------------------------|--------------------------------------------|
| GET     | /api/positions         | Récupère toutes les positions              |
| GET     | /api/positions/{id}    | Récupère une position par son ID           |
| POST    | /api/positions         | Crée une nouvelle position                 |
| PUT     | /api/positions/{id}    | Met à jour une position existante          |
| DELETE  | /api/positions/{id}    | Supprime une position                      |

## 2. Contrôleurs Web (Thymeleaf)

### 2.1 EmployeWebController

Ce contrôleur gère les interactions utilisateur liées aux employés via les vues Thymeleaf.

```java
@Controller
@RequestMapping("/web/employes")
public class EmployeWebController {
    @Autowired
    private EmployeService employeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;
    
    @GetMapping
    public String list(Model model) {
        List<Employe> employes = employeService.getAll();
        model.addAttribute("employes", employes);
        return "employes";
    }
    
    @GetMapping("/new")
    public String newEmploye(Model model) {
        model.addAttribute("employe", new Employe());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "employe-form";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("employe", employeService.getById(id));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "employe-form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute Employe employe) {
        employeService.save(employe);
        return "redirect:/web/employes";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeService.delete(id);
        return "redirect:/web/employes";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam(required = false) Long departmentId,
                         @RequestParam(required = false) Double salaire,
                         Model model) {
        List<Employe> results;
        if (departmentId != null) {
            results = employeService.findByDepartment(departmentId);
        } else if (salaire != null) {
            results = employeService.findBySalaireMin(salaire);
        } else {
            results = employeService.getAll();
        }
        model.addAttribute("employes", results);
        return "employes";
    }
}
```

#### URLs disponibles

| Méthode | URL                           | Vue retournée   | Description                                    |
|---------|-------------------------------|-----------------|------------------------------------------------|
| GET     | /web/employes                 | employes.html   | Liste tous les employés                        |
| GET     | /web/employes/new             | employe-form.html | Formulaire de création d'un employé          |
| GET     | /web/employes/edit/{id}       | employe-form.html | Formulaire d'édition d'un employé            |
| POST    | /web/employes/save            | (redirection)   | Sauvegarde d'un employé (création ou édition)  |
| GET     | /web/employes/delete/{id}     | (redirection)   | Suppression d'un employé                       |
| GET     | /web/employes/search          | employes.html   | Recherche d'employés filtrés                   |

### 2.2 DepartmentWebController

Ce contrôleur gère les interactions utilisateur liées aux départements via les vues Thymeleaf.

```java
@Controller
@RequestMapping("/web/departments")
public class DepartmentWebController {
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments";
    }
    
    @GetMapping("/new")
    public String newDepartment(Model model) {
        model.addAttribute("department", new Department());
        return "department-form";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department-form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/web/departments";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/web/departments";
    }
}
```

### 2.3 PositionWebController

Ce contrôleur gère les interactions utilisateur liées aux positions via les vues Thymeleaf.

```java
@Controller
@RequestMapping("/web/positions")
public class PositionWebController {
    @Autowired
    private PositionService positionService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("positions", positionService.getAllPositions());
        return "positions";
    }
    
    @GetMapping("/new")
    public String newPosition(Model model) {
        model.addAttribute("position", new Position());
        return "position-form";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionService.getPositionById(id));
        return "position-form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute Position position) {
        positionService.savePosition(position);
        return "redirect:/web/positions";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        positionService.deletePosition(id);
        return "redirect:/web/positions";
    }
}
```
