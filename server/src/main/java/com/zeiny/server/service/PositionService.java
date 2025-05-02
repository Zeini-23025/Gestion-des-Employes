package com.zeiny.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeiny.server.model.Position;
import com.zeiny.server.repository.PositionRepository;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElse(null);
    }

    public void savePosition(Position position) {
        positionRepository.save(position);
    }

    public void deletePosition(Long id) {
        positionRepository.deleteById(id);
    }
}
