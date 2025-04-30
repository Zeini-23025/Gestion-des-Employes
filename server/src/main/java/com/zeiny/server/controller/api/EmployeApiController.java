package com.zeiny.server.controller.api;

import com.zeiny.server.model.Employe;
import com.zeiny.server.service.EmployeService;
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
@RequestMapping("/api/employes")
@Tag(name = "Employés", description = "API de gestion des employés")
public class EmployeApiController {

    @Autowired
    private EmployeService employeService;

    @Operation(summary = "Récupérer tous les employés", description = "Récupère la liste complète des employés avec leurs informations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des employés récupérée avec succès",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Employe.class)))
    })
    @GetMapping
    public ResponseEntity<List<Employe>> getAll() {
        return new ResponseEntity<>(employeService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un employé par ID", description = "Récupère les détails d'un employé spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employé trouvé",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Employe.class))),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getById(
            @Parameter(description = "ID de l'employé à récupérer") @PathVariable Long id) {
        Employe employe = employeService.getById(id);
        if (employe != null) {
            return new ResponseEntity<>(employe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Créer un nouvel employé", description = "Ajoute un nouvel employé dans la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Employé créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public ResponseEntity<Employe> create(@RequestBody Employe employe) {
        return new ResponseEntity<>(employeService.save(employe), HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un employé", description = "Met à jour les informations d'un employé existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employé mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employe> update(
            @Parameter(description = "ID de l'employé à mettre à jour") @PathVariable Long id,
            @RequestBody Employe updated) {
        if (employeService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updated.setId(id);
        return new ResponseEntity<>(employeService.save(updated), HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un employé", description = "Supprime un employé de la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Employé supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de l'employé à supprimer") @PathVariable Long id) {
        if (employeService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Récupérer les employés par département", 
               description = "Récupère la liste des employés appartenant à un département spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des employés récupérée avec succès")
    })
    @GetMapping("/by-department/{deptId}")
    public ResponseEntity<List<Employe>> byDepartment(
            @Parameter(description = "ID du département") @PathVariable Long deptId) {
        return new ResponseEntity<>(employeService.findByDepartment(deptId), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer les employés par salaire minimum", 
               description = "Récupère la liste des employés ayant un salaire supérieur ou égal au minimum spécifié")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des employés récupérée avec succès")
    })
    @GetMapping("/by-salaire/{min}")
    public ResponseEntity<List<Employe>> bySalaire(
            @Parameter(description = "Salaire minimum") @PathVariable Double min) {
        return new ResponseEntity<>(employeService.findBySalaireMin(min), HttpStatus.OK);
    }
}