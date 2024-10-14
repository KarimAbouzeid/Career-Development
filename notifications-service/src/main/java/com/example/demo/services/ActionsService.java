package com.example.demo.services;

import com.example.demo.dtos.ActionsDTO;
import com.example.demo.entities.Actions;
import com.example.demo.entities.Notifications;
import com.example.demo.mappers.ActionsMapper;
import com.example.demo.mappers.NotificationMapper;
import com.example.demo.repositories.ActionsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActionsService {

    private final ActionsRepository actionsRepository;
    private final ActionsMapper actionsMapper;


    @Autowired
    public ActionsService(ActionsRepository actionsRepository, ActionsMapper actionsMapper) {
        this.actionsRepository = actionsRepository;
        this.actionsMapper = actionsMapper;
    }

    public List<ActionsDTO> getAllActions() {
        List<Actions> allActions = actionsRepository.findAll();
        return allActions.stream()
                .map(actionsMapper::toActionsDTO)
                .collect(Collectors.toList());
    }

    public ActionsDTO getActionById(UUID id) {
        Actions actions = actionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actions with id " + id + " not found"));

        return actionsMapper.toActionsDTO(actions);
    }

    public void addAction(ActionsDTO actionDTO) {
        Actions action = actionsMapper.toActions(actionDTO);
        actionsRepository.save(action);
    }

    public void deleteAction(UUID id) {
        if (actionsRepository.existsById(id)) {
            actionsRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Actions with id " + id + " does not exist.");
        }
    }
}
