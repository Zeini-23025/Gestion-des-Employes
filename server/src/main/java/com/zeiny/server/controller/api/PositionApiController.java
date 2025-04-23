package com.zeiny.server.controller.api;

import com.zeiny.server.model.Position;
import com.zeiny.server.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionApiController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @GetMapping("/{id}")
    public Position getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id);
    }

    @PostMapping
    public void createPosition(@RequestBody Position position) {
        positionService.savePosition(position);
    }

    @PutMapping("/{id}")
    public void updatePosition(@PathVariable Long id, @RequestBody Position updated) {
        updated.setId(id);
        positionService.savePosition(updated);
    }

    @DeleteMapping("/{id}")
    public void deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
    }
}
