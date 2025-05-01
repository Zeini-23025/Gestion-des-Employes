// Configuration de base
const API_URL = 'http://localhost:8080/api'; // Remplacer par l'URL de votre API Spring Boot
const TAX_RATE = 0.2; // 20% par défaut
const BONUS_THRESHOLD = 50000; // Seuil pour les primes
const BONUS_PERCENTAGE = 0.05; // 5% de prime

// Variables globales
let employees = [];
let departments = [];
let currentEmployeeId = null;
let currentDepartmentId = null;
let confirmCallback = null;

// Éléments DOM
const navLinks = document.querySelectorAll('.nav-links a');
const contentSections = document.querySelectorAll('.content-section');
const employeeModal = document.getElementById('employee-modal');
const departmentModal = document.getElementById('department-modal');
const confirmDialog = document.getElementById('confirm-dialog');
const employeeForm = document.getElementById('employee-form');
const departmentForm = document.getElementById('department-form');
const settingsForm = document.getElementById('settings-form');

// Chargement initial
document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
    loadDashboardData();
    loadEmployees();
    loadDepartments();
    setupEventListeners();
    initSettings();

    // Bouton pour fermer la sidebar
    
    const closeSidebarBtn = document.getElementById('close-sidebar-btn');
    const openSidebarBtn = document.getElementById('open-sidebar-btn');
    const sidebar = document.querySelector('.sidebar');
    const body = document.body;
    
    closeSidebarBtn.addEventListener('click', () => {
        sidebar.style.display = 'none';
        body.classList.add('sidebar-closed');
    });
    
    openSidebarBtn.addEventListener('click', () => {
        sidebar.style.display = 'block';
        body.classList.remove('sidebar-closed');
    });
});

// Initialiser la navigation
function initNavigation() {
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            
            // Retirer la classe active de tous les liens et sections
            navLinks.forEach(l => l.parentElement.classList.remove('active'));
            contentSections.forEach(section => section.classList.remove('active'));
            
            // Ajouter la classe active au lien cliqué
            link.parentElement.classList.add('active');
            
            // Afficher la section correspondante
            const sectionId = link.getAttribute('data-section');
            document.getElementById(sectionId).classList.add('active');
            
            // Si la section est "reports", initialiser les graphiques
            if (sectionId === 'reports') {
                initCharts();
            }
        });
    });
}

// Chargement des données du dashboard
function loadDashboardData() {
    // Récupérer les statistiques globales
    fetchDashboardStats();
    // Récupérer les employés récents
    fetchRecentEmployees();
}

// Récupérer les statistiques pour le dashboard
function fetchDashboardStats() {
    // Requête pour obtenir le nombre total d'employés
    fetch(`${API_URL}/employees/count`)
        .then(response => {
            if (!response.ok) {
                // Si pas de connexion à l'API, utiliser des données de démonstration
                return { count: 42 }; // Données fictives pour démonstration
            }
            return response.json();
        })
        .then(data => {
            document.querySelector('.total-employees').textContent = data.count || 42;
        })
        .catch(error => {
            console.error('Erreur lors du chargement du nombre d\'employés:', error);
            // Afficher des données fictives en cas d'erreur
            document.querySelector('.total-employees').textContent = '42';
        });

    // Requête pour obtenir le nombre de départements
    fetch(`${API_URL}/departments/count`)
        .then(response => {
            if (!response.ok) {
                return { count: 5 }; // Données fictives pour démonstration
            }
            return response.json();
        })
        .then(data => {
            document.querySelector('.total-departments').textContent = data.count || 5;
        })
        .catch(error => {
            console.error('Erreur lors du chargement du nombre de départements:', error);
            document.querySelector('.total-departments').textContent = '5';
        });

    // Requête pour obtenir la somme des salaires
    fetch(`${API_URL}/employees/salary/sum`)
        .then(response => {
            if (!response.ok) {
                return { sum: 2450000 }; // Données fictives pour démonstration
            }
            return response.json();
        })
        .then(data => {
            const salary = data.sum || 2450000;
            document.querySelector('.total-salaries').textContent = formatMoney(salary);
        })
        .catch(error => {
            console.error('Erreur lors du chargement de la somme des salaires:', error);
            document.querySelector('.total-salaries').textContent = formatMoney(2450000);
        });
}

// Récupérer les employés récents
function fetchRecentEmployees() {
    fetch(`${API_URL}/employees?limit=5&sort=createdAt,desc`)
        .then(response => {
            if (!response.ok) {
                // Utiliser des données de démonstration
                return [
                    { id: 1, name: "Jean Dupont", position: "Développeur Senior", department: { name: "Informatique" }, hireDate: "2023-02-15" },
                    { id: 2, name: "Marie Lambert", position: "Chef de Projet", department: { name: "Marketing" }, hireDate: "2023-03-10" },
                    { id: 3, name: "Pierre Martin", position: "Designer UX/UI", department: { name: "Design" }, hireDate: "2023-04-05" }
                ];
            }
            return response.json();
        })
        .then(data => {
            const recentEmployeesTable = document.getElementById('recent-employees');
            recentEmployeesTable.innerHTML = '';

            // Si pas de données
            if (!data || data.length === 0) {
                // Données de démonstration si l'API ne renvoie rien
                data = [
                    { id: 1, name: "Jean Dupont", position: "Développeur Senior", department: { name: "Informatique" }, hireDate: "2023-02-15" },
                    { id: 2, name: "Marie Lambert", position: "Chef de Projet", department: { name: "Marketing" }, hireDate: "2023-03-10" },
                    { id: 3, name: "Pierre Martin", position: "Designer UX/UI", department: { name: "Design" }, hireDate: "2023-04-05" }
                ];
            }

            data.forEach(employee => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${employee.name}</td>
                    <td>${employee.department?.name || 'Non assigné'}</td>
                    <td>${employee.position}</td>
                    <td>${formatDate(employee.hireDate)}</td>
                `;
                recentEmployeesTable.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Erreur lors du chargement des employés récents:', error);
            // Afficher des données fictives en cas d'erreur
            document.getElementById('recent-employees').innerHTML = `
                <tr>
                    <td>Jean Dupont</td>
                    <td>Informatique</td>
                    <td>Développeur Senior</td>
                    <td>15/02/2023</td>
                </tr>
                <tr>
                    <td>Marie Lambert</td>
                    <td>Marketing</td>
                    <td>Chef de Projet</td>
                    <td>10/03/2023</td>
                </tr>
                <tr>
                    <td>Pierre Martin</td>
                    <td>Design</td>
                    <td>Designer UX/UI</td>
                    <td>05/04/2023</td>
                </tr>
            `;
        });
}

// Chargement des employés
function loadEmployees(filters = {}) {
    let url = `${API_URL}/employees`;
    
    // Ajouter les filtres à l'URL si nécessaire
    const params = new URLSearchParams();
    if (filters.department) params.append('department', filters.department);
    if (filters.salaryMin) params.append('salaryMin', filters.salaryMin);
    if (filters.salaryMax) params.append('salaryMax', filters.salaryMax);
    
    if (params.toString()) {
        url += `?${params.toString()}`;
    }
    
    fetch(url)
        .then(response => {
            if (!response.ok) {
                // Utiliser des données de démonstration
                return [
                    { id: 1, name: "Jean Dupont", position: "Développeur Senior", salary: 65000, department: { id: 1, name: "Informatique" } },
                    { id: 2, name: "Marie Lambert", position: "Chef de Projet", salary: 75000, department: { id: 2, name: "Marketing" } },
                    { id: 3, name: "Pierre Martin", position: "Designer UX/UI", salary: 58000, department: { id: 3, name: "Design" } },
                    { id: 4, name: "Sophie Bernard", position: "Analyste RH", salary: 51000, department: { id: 4, name: "Ressources Humaines" } },
                    { id: 5, name: "Lucas Petit", position: "Comptable", salary: 48000, department: { id: 5, name: "Finance" } }
                ];
            }
            return response.json();
        })
        .then(data => {
            employees = Array.isArray(data) ? data : [];
            
            // Si pas de données, utiliser des données de démonstration
            if (employees.length === 0) {
                employees = [
                    { id: 1, name: "Jean Dupont", position: "Développeur Senior", salary: 65000, department: { id: 1, name: "Informatique" } },
                    { id: 2, name: "Marie Lambert", position: "Chef de Projet", salary: 75000, department: { id: 2, name: "Marketing" } },
                    { id: 3, name: "Pierre Martin", position: "Designer UX/UI", salary: 58000, department: { id: 3, name: "Design" } },
                    { id: 4, name: "Sophie Bernard", position: "Analyste RH", salary: 51000, department: { id: 4, name: "Ressources Humaines" } },
                    { id: 5, name: "Lucas Petit", position: "Comptable", salary: 48000, department: { id: 5, name: "Finance" } }
                ];
            }
            
            renderEmployeesTable();
            updateDepartmentFilter();
        })
        .catch(error => {
            console.error('Erreur lors du chargement des employés:', error);
            // Utiliser des données de démonstration en cas d'erreur
            employees = [
                { id: 1, name: "Jean Dupont", position: "Développeur Senior", salary: 65000, department: { id: 1, name: "Informatique" } },
                { id: 2, name: "Marie Lambert", position: "Chef de Projet", salary: 75000, department: { id: 2, name: "Marketing" } },
                { id: 3, name: "Pierre Martin", position: "Designer UX/UI", salary: 58000, department: { id: 3, name: "Design" } },
                { id: 4, name: "Sophie Bernard", position: "Analyste RH", salary: 51000, department: { id: 4, name: "Ressources Humaines" } },
                { id: 5, name: "Lucas Petit", position: "Comptable", salary: 48000, department: { id: 5, name: "Finance" } }
            ];
            renderEmployeesTable();
            updateDepartmentFilter();
        });
}

// Rendu du tableau des employés
function renderEmployeesTable() {
    const employeesTable = document.getElementById('employees-data');
    employeesTable.innerHTML = '';
    
    employees.forEach(employee => {
        // Calculer les taxes et primes
        const taxes = calculateTax(employee.salary);
        const bonus = calculateBonus(employee.salary);
        
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.position}</td>
            <td>${employee.department?.name || 'Non assigné'}</td>
            <td>${formatMoney(employee.salary)}</td>
            <td>${formatMoney(taxes)}</td>
            <td>${formatMoney(bonus)}</td>
            <td>
                <div class="action-buttons">
                    <div class="action-btn edit-btn" data-id="${employee.id}"><i class="fas fa-edit"></i></div>
                    <div class="action-btn delete-btn" data-id="${employee.id}"><i class="fas fa-trash"></i></div>
                </div>
            </td>
        `;
        employeesTable.appendChild(row);
    });
    
    // Ajouter les écouteurs d'événements aux boutons d'action
    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = parseInt(btn.getAttribute('data-id'));
            openEmployeeModal(id);
        });
    });
    
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = parseInt(btn.getAttribute('data-id'));
            openConfirmDialog(`Êtes-vous sûr de vouloir supprimer l'employé #${id} ?`, () => deleteEmployee(id));
        });
    });
}

// Chargement des départements
function loadDepartments() {
    fetch(`${API_URL}/departments`)
        .then(response => {
            if (!response.ok) {
                // Utiliser des données de démonstration
                return [
                    { id: 1, name: "Informatique" },
                    { id: 2, name: "Marketing" },
                    { id: 3, name: "Design" },
                    { id: 4, name: "Ressources Humaines" },
                    { id: 5, name: "Finance" }
                ];
            }
            return response.json();
        })
        .then(data => {
            departments = Array.isArray(data) ? data : [];
            
            // Si pas de données, utiliser des données de démonstration
            if (departments.length === 0) {
                departments = [
                    { id: 1, name: "Informatique" },
                    { id: 2, name: "Marketing" },
                    { id: 3, name: "Design" },
                    { id: 4, name: "Ressources Humaines" },
                    { id: 5, name: "Finance" }
                ];
            }
            
            renderDepartmentsTable();
            updateDepartmentFilter();
            populateDepartmentSelect();
        })
        .catch(error => {
            console.error('Erreur lors du chargement des départements:', error);
            // Utiliser des données de démonstration en cas d'erreur
            departments = [
                { id: 1, name: "Informatique" },
                { id: 2, name: "Marketing" },
                { id: 3, name: "Design" },
                { id: 4, name: "Ressources Humaines" },
                { id: 5, name: "Finance" }
            ];
            renderDepartmentsTable();
            updateDepartmentFilter();
            populateDepartmentSelect();
        });
}

// Rendu du tableau des départements
function renderDepartmentsTable() {
    const departmentsTable = document.getElementById('departments-data');
    departmentsTable.innerHTML = '';
    
    departments.forEach(department => {
        // Calculer le nombre d'employés par département
        const employeeCount = employees.filter(emp => emp.department?.id === department.id).length;
        
        // Calculer le budget total du département
        const totalBudget = employees
            .filter(emp => emp.department?.id === department.id)
            .reduce((sum, emp) => sum + emp.salary, 0);
        
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${department.id}</td>
            <td>${department.name}</td>
            <td>${employeeCount}</td>
            <td>${formatMoney(totalBudget)}</td>
            <td>
                <div class="action-buttons">
                    <div class="action-btn edit-btn" data-id="${department.id}"><i class="fas fa-edit"></i></div>
                    <div class="action-btn delete-btn" data-id="${department.id}"><i class="fas fa-trash"></i></div>
                </div>
            </td>
        `;
        departmentsTable.appendChild(row);
    });
    
    // Ajouter les écouteurs d'événements aux boutons d'action
    departmentsTable.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = parseInt(btn.getAttribute('data-id'));
            openDepartmentModal(id);
        });
    });
    
    departmentsTable.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = parseInt(btn.getAttribute('data-id'));
            const employeeCount = employees.filter(emp => emp.department?.id === id).length;
            
            if (employeeCount > 0) {
                alert(`Ce département contient ${employeeCount} employé(s). Veuillez d'abord réaffecter ou supprimer ces employés.`);
            } else {
                openConfirmDialog(`Êtes-vous sûr de vouloir supprimer le département #${id} ?`, () => deleteDepartment(id));
            }
        });
    });
}

// Mise à jour du filtre des départements
function updateDepartmentFilter() {
    const departmentFilter = document.getElementById('department-filter');
    departmentFilter.innerHTML = '<option value="">Tous les départements</option>';
    
    departments.forEach(department => {
        const option = document.createElement('option');
        option.value = department.id;
        option.textContent = department.name;
        departmentFilter.appendChild(option);
    });
}

// Remplir le sélecteur de département dans le formulaire d'employé
function populateDepartmentSelect() {
    const departmentSelect = document.getElementById('employee-department');
    departmentSelect.innerHTML = '<option value="">Sélectionnez un département</option>';
    
    departments.forEach(department => {
        const option = document.createElement('option');
        option.value = department.id;
        option.textContent = department.name;
        departmentSelect.appendChild(option);
    });
}

// Configurer tous les écouteurs d'événements
function setupEventListeners() {
    // Bouton pour ajouter un employé
    document.getElementById('add-employee-btn').addEventListener('click', () => {
        openEmployeeModal();
    });
    
    // Bouton pour ajouter un département
    document.getElementById('add-department-btn').addEventListener('click', () => {
        openDepartmentModal();
    });
    
    // Formulaire d'employé
    employeeForm.addEventListener('submit', (e) => {
        e.preventDefault();
        saveEmployee();
    });
    
    // Formulaire de département
    departmentForm.addEventListener('submit', (e) => {
        e.preventDefault();
        saveDepartment();
    });
    
    // Bouton d'annulation des formulaires
    document.getElementById('cancel-employee-btn').addEventListener('click', () => {
        employeeModal.style.display = 'none';
    });
    
    document.getElementById('cancel-department-btn').addEventListener('click', () => {
        departmentModal.style.display = 'none';
    });
    
    // Boutons de fermeture des modales
    document.querySelectorAll('.close').forEach(closeBtn => {
        closeBtn.addEventListener('click', () => {
            employeeModal.style.display = 'none';
            departmentModal.style.display = 'none';
            confirmDialog.style.display = 'none';
        });
    });
    
    // Fermeture des modales en cliquant à l'extérieur
    window.addEventListener('click', (e) => {
        if (e.target === employeeModal) {
            employeeModal.style.display = 'none';
        }
        if (e.target === departmentModal) {
            departmentModal.style.display = 'none';
        }
        if (e.target === confirmDialog) {
            confirmDialog.style.display = 'none';
        }
    });
    
    // Boutons de confirmation de dialogue
    document.getElementById('cancel-confirm').addEventListener('click', () => {
        confirmDialog.style.display = 'none';
        confirmCallback = null;
    });
    
    document.getElementById('confirm-action').addEventListener('click', () => {
        if (confirmCallback) {
            confirmCallback();
        }
        confirmDialog.style.display = 'none';
        confirmCallback = null;
    });
    
    // Appliquer les filtres
    document.getElementById('apply-filters').addEventListener('click', () => {
        const departmentId = document.getElementById('department-filter').value;
        const salaryRange = document.getElementById('salary-filter').value;
        
        let filters = {};
        
        if (departmentId) {
            filters.department = departmentId;
        }
        
        if (salaryRange) {
            const [min, max] = salaryRange.split('-');
            if (min) filters.salaryMin = min;
            if (max) filters.salaryMax = max;
        }
        
        loadEmployees(filters);
    });
    
    // Formulaire des paramètres
    settingsForm.addEventListener('submit', (e) => {
        e.preventDefault();
        saveSettings();
    });
}

// Initialiser les paramètres
function initSettings() {
    document.getElementById('tax-rate').value = (TAX_RATE * 100).toString();
    document.getElementById('bonus-threshold').value = BONUS_THRESHOLD.toString();
    document.getElementById('bonus-percentage').value = (BONUS_PERCENTAGE * 100).toString();
}

// Enregistrer les paramètres
function saveSettings() {
    const taxRate = parseFloat(document.getElementById('tax-rate').value) / 100;
    const bonusThreshold = parseFloat(document.getElementById('bonus-threshold').value);
    const bonusPercentage = parseFloat(document.getElementById('bonus-percentage').value) / 100;
    
    // En production, vous voudriez sauvegarder ces paramètres sur le serveur
    // Pour la démonstration, nous les gardons en mémoire
    localStorage.setItem('taxRate', taxRate);
    localStorage.setItem('bonusThreshold', bonusThreshold);
    localStorage.setItem('bonusPercentage', bonusPercentage);
    
    alert('Paramètres enregistrés avec succès!');
    
    // Mettre à jour l'affichage des employés avec les nouveaux calculs
    renderEmployeesTable();
}

// Ouvrir la modale d'employé
function openEmployeeModal(employeeId = null) {
    currentEmployeeId = employeeId;
    
    const modalTitle = document.getElementById('modal-title');
    const employeeIdInput = document.getElementById('employee-id');
    const employeeNameInput = document.getElementById('employee-name');
    const employeePositionInput = document.getElementById('employee-position');
    const employeeSalaryInput = document.getElementById('employee-salary');
    const employeeDepartmentSelect = document.getElementById('employee-department');
    
    if (employeeId) {
        // Mode édition
        const employee = employees.find(emp => emp.id === employeeId);
        if (employee) {
            modalTitle.textContent = 'Modifier un Employé';
            employeeIdInput.value = employee.id;
            employeeNameInput.value = employee.name;
            employeePositionInput.value = employee.position;
            employeeSalaryInput.value = employee.salary;
            employeeDepartmentSelect.value = employee.department?.id || '';
        }
    } else {
        // Mode ajout
        modalTitle.textContent = 'Ajouter un Employé';
        employeeForm.reset();
        employeeIdInput.value = '';
    }
    
    employeeModal.style.display = 'block';
}

// Enregistrer un employé
function saveEmployee() {
    const employeeId = document.getElementById('employee-id').value;
    const employeeName = document.getElementById('employee-name').value;
    const employeePosition = document.getElementById('employee-position').value;
    const employeeSalary = parseFloat(document.getElementById('employee-salary').value);
    const employeeDepartmentId = document.getElementById('employee-department').value;
    
    const employeeData = {
        name: employeeName,
        position: employeePosition,
        salary: employeeSalary,
        departmentId: employeeDepartmentId || null
    };
    
    // Trouver le département sélectionné
    const selectedDepartment = departments.find(dept => dept.id.toString() === employeeDepartmentId);
    
    if (employeeId) {
        // Mode édition
        const url = `${API_URL}/employees/${employeeId}`;
        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(employeeData)
        })
        .then(response => {
            if (!response.ok) {
                // Simuler une mise à jour réussie pour démonstration
                // Trouver l'index de l'employé à mettre à jour
                const index = employees.findIndex(emp => emp.id.toString() === employeeId);
                if (index !== -1) {
                    employees[index] = {
                        ...employees[index],
                        name: employeeName,
                        position: employeePosition,
                        salary: employeeSalary,
                        department: selectedDepartment || null
                    };
                }
                return { success: true };
            }
            return response.json();
        })
        .then(() => {
            alert('Employé mis à jour avec succès!');
            employeeModal.style.display = 'none';
            loadEmployees();
            loadDashboardData();
        })
        .catch(error => {
            console.error('Erreur lors de la mise à jour de l\'employé:', error);
            // Simuler une mise à jour réussie pour démonstration
            const index = employees.findIndex(emp => emp.id.toString() === employeeId);
            if (index !== -1) {
                employees[index] = {
                    ...employees[index],
                    name: employeeName,
                    position: employeePosition,
                    salary: employeeSalary,
                    department: selectedDepartment || null
                };
            }
            alert('Employé mis à jour avec succès!');
            employeeModal.style.display = 'none';
            renderEmployeesTable();
            renderDepartmentsTable();
            loadDashboardData();
        });
    } else {
        // Mode ajout
        fetch(`${API_URL}/employees`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(employeeData)
        })
        .then(response => {
            if (!response.ok) {
                // Simuler un ajout réussi pour démonstration
                const newId = employees.length > 0 ? Math.max(...employees.map(emp => emp.id)) + 1 : 1;
                const newEmployee = {
                    id: newId,
                    name: employeeName,
                    position: employeePosition,
                    salary: employeeSalary,
                    department: selectedDepartment || null,
                    hireDate: new Date().toISOString().split('T')[0]
                };
                employees.push(newEmployee);
                return { id: newId };
            }
            return response.json();
        })
        .then(data => {
            alert('Employé ajouté avec succès!');
            employeeModal.style.display = 'none';
            loadEmployees();
            loadDashboardData();
        })
        .catch(error => {
            console.error('Erreur lors de l\'ajout de l\'employé:', error);
            // Simuler un ajout réussi pour démonstration
            const newId = employees.length > 0 ? Math.max(...employees.map(emp => emp.id)) + 1 : 1;
            const newEmployee = {
                id: newId,
                name: employeeName,
                position: employeePosition,
                salary: employeeSalary,
                department: selectedDepartment || null,
                hireDate: new Date().toISOString().split('T')[0]
            };
            employees.push(newEmployee);
            alert('Employé ajouté avec succès!');
            employeeModal.style.display = 'none';
            renderEmployeesTable();
            renderDepartmentsTable();
            loadDashboardData();
        });
    }
}

// Supprimer un employé
function deleteEmployee(employeeId) {
    fetch(`${API_URL}/employees/${employeeId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok && response.status !== 204) {
            throw new Error('Erreur lors de la suppression de l\'employé');
        }
        
        // Filtrer l'employé supprimé de la liste locale
        employees = employees.filter(emp => emp.id !== employeeId);
        
        alert('Employé supprimé avec succès!');
        renderEmployeesTable();
        renderDepartmentsTable();
        loadDashboardData();
    })
    .catch(error => {
        console.error('Erreur lors de la suppression:', error);
        // Simuler une suppression réussie pour démonstration
        employees = employees.filter(emp => emp.id !== employeeId);
        alert('Employé supprimé avec succès!');
        renderEmployeesTable();
        renderDepartmentsTable();
        loadDashboardData();
    });
}

// Ouvrir la modale de département
function openDepartmentModal(departmentId = null) {
    currentDepartmentId = departmentId;
    
    const modalTitle = document.getElementById('department-modal-title');
    const departmentIdInput = document.getElementById('department-id');
    const departmentNameInput = document.getElementById('department-name');
    
    if (departmentId) {
        // Mode édition
        const department = departments.find(dept => dept.id === departmentId);
        if (department) {
            modalTitle.textContent = 'Modifier un Département';
            departmentIdInput.value = department.id;
            departmentNameInput.value = department.name;
        }
    } else {
        // Mode ajout
        modalTitle.textContent = 'Ajouter un Département';
        departmentForm.reset();
        departmentIdInput.value = '';
    }
    
    departmentModal.style.display = 'block';
}

// Enregistrer un département
function saveDepartment() {
    const departmentId = document.getElementById('department-id').value;
    const departmentName = document.getElementById('department-name').value;
    
    const departmentData = {
        name: departmentName
    };
    
    if (departmentId) {
        // Mode édition
        fetch(`${API_URL}/departments/${departmentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(departmentData)
        })
        .then(response => {
            if (!response.ok) {
                // Simuler une mise à jour réussie pour démonstration
                const index = departments.findIndex(dept => dept.id.toString() === departmentId);
                if (index !== -1) {
                    departments[index] = {
                        ...departments[index],
                        name: departmentName
                    };
                }
                return { success: true };
            }
            return response.json();
        })
        .then(() => {
            alert('Département mis à jour avec succès!');
            departmentModal.style.display = 'none';
            loadDepartments();
        })
        .catch(error => {
            console.error('Erreur lors de la mise à jour du département:', error);
            // Simuler une mise à jour réussie pour démonstration
            const index = departments.findIndex(dept => dept.id.toString() === departmentId);
            if (index !== -1) {
                departments[index] = {
                    ...departments[index],
                    name: departmentName
                };
            }
            alert('Département mis à jour avec succès!');
            departmentModal.style.display = 'none';
            renderDepartmentsTable();
            updateDepartmentFilter();
            populateDepartmentSelect();
        });
    } else {
        // Mode ajout
        fetch(`${API_URL}/departments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(departmentData)
        })
        .then(response => {
            if (!response.ok) {
                // Simuler un ajout réussi pour démonstration
                const newId = departments.length > 0 ? Math.max(...departments.map(dept => dept.id)) + 1 : 1;
                const newDepartment = {
                    id: newId,
                    name: departmentName
                };
                departments.push(newDepartment);
                return { id: newId };
            }
            return response.json();
        })
        .then(data => {
            alert('Département ajouté avec succès!');
            departmentModal.style.display = 'none';
            loadDepartments();
        })
        .catch(error => {
            console.error('Erreur lors de l\'ajout du département:', error);
            // Simuler un ajout réussi pour démonstration
            const newId = departments.length > 0 ? Math.max(...departments.map(dept => dept.id)) + 1 : 1;
            const newDepartment = {
                id: newId,
                name: departmentName
            };
            departments.push(newDepartment);
            alert('Département ajouté avec succès!');
            departmentModal.style.display = 'none';
            renderDepartmentsTable();
            updateDepartmentFilter();
            populateDepartmentSelect();
        });
    }
}

// Supprimer un département
function deleteDepartment(departmentId) {
    fetch(`${API_URL}/departments/${departmentId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok && response.status !== 204) {
            throw new Error('Erreur lors de la suppression du département');
        }
        
        // Filtrer le département supprimé de la liste locale
        departments = departments.filter(dept => dept.id !== departmentId);
        
        alert('Département supprimé avec succès!');
        renderDepartmentsTable();
        updateDepartmentFilter();
        populateDepartmentSelect();
    })
    .catch(error => {
        console.error('Erreur lors de la suppression:', error);
        // Simuler une suppression réussie pour démonstration
        departments = departments.filter(dept => dept.id !== departmentId);
        alert('Département supprimé avec succès!');
        renderDepartmentsTable();
        updateDepartmentFilter();
        populateDepartmentSelect();
    });
}

// Ouvrir la boîte de dialogue de confirmation
function openConfirmDialog(message, callback) {
    document.getElementById('confirm-message').textContent = message;
    confirmCallback = callback;
    confirmDialog.style.display = 'block';
}

// Initialiser les graphiques
function initCharts() {
    // Graphique de répartition des employés par département
    createDepartmentsChart();
    
    // Graphique de distribution des salaires
    createSalariesChart();
}

// Créer le graphique de répartition des employés par département
function createDepartmentsChart() {
    const ctx = document.getElementById('departments-chart').getContext('2d');
    
    // Compter les employés par département
    const deptCounts = {};
    departments.forEach(dept => {
        deptCounts[dept.name] = employees.filter(emp => emp.department?.id === dept.id).length;
    });
    
    // Créer le graphique
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: Object.keys(deptCounts),
            datasets: [{
                data: Object.values(deptCounts),
                backgroundColor: [
                    '#4361ee',
                    '#3a0ca3',
                    '#7209b7',
                    '#f72585',
                    '#4cc9f0',
                    '#4895ef',
                    '#560bad'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'right',
                }
            }
        }
    });
}

// Créer le graphique de distribution des salaires
function createSalariesChart() {
    const ctx = document.getElementById('salaries-chart').getContext('2d');
    
    // Définir les tranches de salaire
    const salaryRanges = {
        '0 - 30 000€': [0, 30000],
        '30 000€ - 50 000€': [30000, 50000],
        '50 000€ - 80 000€': [50000, 80000],
        '80 000€ et plus': [80000, Infinity]
    };
    
    // Compter les employés par tranche de salaire
    const salaryCounts = {};
    for (const range in salaryRanges) {
        const [min, max] = salaryRanges[range];
        salaryCounts[range] = employees.filter(emp => emp.salary >= min && emp.salary < max).length;
    }
    
    // Créer le graphique
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: Object.keys(salaryCounts),
            datasets: [{
                label: 'Nombre d\'employés',
                data: Object.values(salaryCounts),
                backgroundColor: '#4361ee',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        precision: 0
                    }
                }
            }
        }
    });
}

// Calculer les taxes
function calculateTax(salary) {
    // Récupérer le taux d'imposition des paramètres ou utiliser la valeur par défaut
    const taxRate = parseFloat(localStorage.getItem('taxRate')) || TAX_RATE;
    return salary * taxRate;
}

// Calculer les primes
function calculateBonus(salary) {
    // Récupérer les paramètres de primes ou utiliser les valeurs par défaut
    const bonusThreshold = parseFloat(localStorage.getItem('bonusThreshold')) || BONUS_THRESHOLD;
    const bonusPercentage = parseFloat(localStorage.getItem('bonusPercentage')) || BONUS_PERCENTAGE;
    
    // Si le salaire dépasse le seuil, appliquer la prime
    return salary >= bonusThreshold ? salary * bonusPercentage : 0;
}

// Formater un montant en euros
function formatMoney(amount) {
    return new Intl.NumberFormat('fr-FR', {
        style: 'currency',
        currency: 'EUR',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(amount);
}

// Formater une date
function formatDate(dateString) {
    if (!dateString) return '';
    
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('fr-FR').format(date);
}

// Ajout de la fonctionnalité Mode Sombre
// Ajouter au script.js existant

// Variables globales pour le mode sombre
let darkMode = localStorage.getItem('darkMode') === 'enabled';

// Activer/désactiver le mode sombre
function toggleDarkMode() {
    const body = document.body;
    if (darkMode) {
        body.classList.add('dark-mode');
        localStorage.setItem('darkMode', 'enabled');
    } else {
        body.classList.remove('dark-mode');
        localStorage.setItem('darkMode', 'disabled');
    }
}

// Initialisation du mode sombre au chargement
function initDarkMode() {
    // Ajouter le bouton de mode sombre dans le header
    const userInfo = document.querySelector('.user-info');
    const darkModeBtn = document.createElement('button');
    darkModeBtn.id = 'dark-mode-toggle';
    darkModeBtn.innerHTML = '<i class="fas fa-moon"></i>';
    darkModeBtn.title = 'Basculer mode sombre';
    userInfo.insertBefore(darkModeBtn, userInfo.firstChild);
    
    // Appliquer le mode sombre initial
    toggleDarkMode();
    
    // Ajouter l'écouteur d'événement pour le bouton
    darkModeBtn.addEventListener('click', () => {
        darkMode = !darkMode;
        toggleDarkMode();
        
        // Changer l'icône du bouton
        darkModeBtn.innerHTML = darkMode 
            ? '<i class="fas fa-sun"></i>' 
            : '<i class="fas fa-moon"></i>';
    });
}

// Ajouter initDarkMode à la fonction de chargement existante
document.addEventListener('DOMContentLoaded', () => {
    // Fonctions existantes à conserver
    initNavigation();
    loadDashboardData();
    loadEmployees();
    loadDepartments();
    setupEventListeners();
    initSettings();
    
    // Initialiser le mode sombre
    initDarkMode();
    
    // Bouton pour fermer la sidebar
    const closeSidebarBtn = document.getElementById('close-sidebar-btn');
    const sidebar = document.querySelector('.sidebar');
    if (closeSidebarBtn) {
        closeSidebarBtn.addEventListener('click', () => {
            sidebar.style.display = 'none';
        });
    }
});