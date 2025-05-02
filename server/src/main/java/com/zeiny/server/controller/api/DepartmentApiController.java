package com.zeiny.server.controller.api;

import com.zeiny.server.model.Department;
import com.zeiny.server.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Départements", description = "API de gestion des départements")
public class DepartmentApiController {

    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = "Récupérer tous les départements", description = "Récupère la liste complète des départements")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des départements récupérée avec succès",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Department.class)))
    })
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un département par ID", description = "Récupère les détails d'un département spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Département trouvé",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Department.class))),
        @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(
            @Parameter(description = "ID du département à récupérer") @PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            return new ResponseEntity<>(department, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Créer un nouveau département", description = "Ajoute un nouveau département dans la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Département créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        departmentService.saveDepartment(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un département", description = "Met à jour les informations d'un département existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Département mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Département non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @Parameter(description = "ID du département à mettre à jour") @PathVariable Long id,
            @RequestBody Department updated) {
        if (departmentService.getDepartmentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updated.setId(id);
        departmentService.saveDepartment(updated);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un département", description = "Supprime un département de la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Département supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Département non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @Parameter(description = "ID du département à supprimer") @PathVariable Long id) {
        if (departmentService.getDepartmentById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}