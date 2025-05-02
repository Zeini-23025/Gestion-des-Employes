package com.zeiny.server.controller.api;

import com.zeiny.server.model.Position;
import com.zeiny.server.service.PositionService;
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
@RequestMapping("/api/positions")
@Tag(name = "Positions", description = "API de gestion des positions (postes)")
public class PositionApiController {

    @Autowired
    private PositionService positionService;

    @Operation(summary = "Récupérer toutes les positions", description = "Récupère la liste complète des positions (postes)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des positions récupérée avec succès",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Position.class)))
    })
    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        return new ResponseEntity<>(positionService.getAllPositions(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer une position par ID", description = "Récupère les détails d'une position spécifique par son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Position trouvée",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Position.class))),
        @ApiResponse(responseCode = "404", description = "Position non trouvée",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(
            @Parameter(description = "ID de la position à récupérer") @PathVariable Long id) {
        Position position = positionService.getPositionById(id);
        if (position != null) {
            return new ResponseEntity<>(position, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Créer une nouvelle position", description = "Ajoute une nouvelle position dans la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Position créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Position position) {
        positionService.savePosition(position);
        return new ResponseEntity<>(position, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour une position", description = "Met à jour les informations d'une position existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Position mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Position non trouvée"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Position> updatePosition(
            @Parameter(description = "ID de la position à mettre à jour") @PathVariable Long id,
            @RequestBody Position updated) {
        if (positionService.getPositionById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updated.setId(id);
        positionService.savePosition(updated);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer une position", description = "Supprime une position de la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Position supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Position non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(
            @Parameter(description = "ID de la position à supprimer") @PathVariable Long id) {
        if (positionService.getPositionById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        positionService.deletePosition(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}