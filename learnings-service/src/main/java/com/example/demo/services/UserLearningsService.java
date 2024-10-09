package com.example.demo.services;

import com.example.demo.dtos.SubmitUserLearningDTO;
import com.example.demo.dtos.UserLearningResponseDTO;
import com.example.demo.dtos.UserLearningsDTO;
import com.example.demo.entities.*;
import com.example.demo.enums.ApprovalStatus;
import com.example.demo.mappers.UserLearningsMapper;
import com.example.demo.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserLearningsService {

    private final UserLearningsRepository userLearningsRepository;
    private final LearningsRepository learningsRepository;
    private final LearningTypesRepository learningTypesRepository;
    private final LearningSubjectsRepository learningSubjectsRepository;
    private final ProofTypesRepository proofTypesRepository;
    private final BoostersRepository boostersRepository;

    private final UserLearningsMapper userLearningsMapper;


    @Autowired
    public UserLearningsService(UserLearningsRepository userLearningsRepository, LearningsRepository learningsRepository, LearningTypesRepository learningTypesRepository, LearningSubjectsRepository learningSubjectsRepository, ProofTypesRepository proofTypesRepository, BoostersRepository boostersRepository, UserLearningsMapper userLearningsMapper) {
        this.userLearningsRepository = userLearningsRepository;
        this.learningsRepository = learningsRepository;
        this.learningTypesRepository = learningTypesRepository;
        this.learningSubjectsRepository = learningSubjectsRepository;
        this.proofTypesRepository = proofTypesRepository;
        this.boostersRepository = boostersRepository;
        this.userLearningsMapper = userLearningsMapper;
    }

    public void submitUserLearning(SubmitUserLearningDTO dto) {
        UserLearnings userLearning = new UserLearnings();

        // Link to an existing learning if provided
        if (dto.getLearningId() != null) {
            Learnings existingLearning = learningsRepository.findById(dto.getLearningId())
                    .orElseThrow(() -> new EntityNotFoundException("Learning not found"));
            userLearning.setLearning(existingLearning);
        } else {
            // Create a new Learning entity if not provided
            Learnings newLearning = new Learnings();
            newLearning.setTitle(dto.getTitle());
            newLearning.setLearningType(findLearningTypeById(dto.getLearningTypeId()));
            newLearning.setURL(dto.getURL());
            newLearning.setDescription(dto.getDescription());
            newLearning.setLearningSubject(findLearningSubjectById(dto.getLearningSubjectId()));
            newLearning.setLengthInHours(dto.getLengthInHours());

            learningsRepository.save(newLearning);
            userLearning.setLearning(newLearning);
        }

        // Set other fields for UserLearnings
        userLearning.setUserId(dto.getUserId());
        userLearning.setProof(dto.getProof());
        userLearning.setProofType(findProofTypeById(dto.getProofTypeId()));
        userLearning.setActiveBooster(findBoosterById(dto.getActiveBoosterId()));
        userLearning.setDate(dto.getDate());
        userLearning.setApprovalStatus(dto.getApprovalStatus());
        userLearning.setComment(dto.getComment());

        // Save user learning
        userLearningsRepository.save(userLearning);

        // Return the mapped DTO
        userLearningsMapper.toUserLearningsDTO(userLearning);
    }

    // Helper methods to find related entities
    private LearningTypes findLearningTypeById(UUID id) {
        return learningTypesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning Type not found"));
    }

    private LearningSubjects findLearningSubjectById(UUID id) {
        return learningSubjectsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning Subject not found"));
    }

    private ProofTypes findProofTypeById(UUID id) {
        return proofTypesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proof Type not found"));
    }

    private Boosters findBoosterById(UUID id) {
        return id != null ? boostersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booster not found")) : null;
    }




    public List<UserLearningsDTO> getSubmittedLearningsByUser(UUID userId) {
        List<UserLearnings> submittedLearnings = userLearningsRepository.findByUserId(userId);
        return submittedLearnings.stream()
                .map(userLearningsMapper::toUserLearningsDTO)
                .collect(Collectors.toList());
    }



    public List<UserLearningResponseDTO> getSubmittedLearningsDetails(UUID userId) {
        List<UserLearnings> userLearningsList = userLearningsRepository.findByUserId(userId);

        // Map to DTO
        return userLearningsList.stream().map(userLearning -> new UserLearningResponseDTO(
                userLearning.getLearning().getTitle(),
                userLearning.getLearning().getURL(),
                userLearning.getProof(),
                userLearning.getProofType().getName(),
                userLearning.getDate(),
                userLearning.getApprovalStatus(),
                userLearning.getComment(),
                userLearning.getLearning().getLengthInHours(),
                userLearning.getLearning().getLearningType().getBaseScore() // Accessing base score
        )).collect(Collectors.toList());
    }

    public UserLearningsDTO getSubmittedLearningById(UUID id) {
        UserLearnings learning = userLearningsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning not found with id " + id));
        return userLearningsMapper.toUserLearningsDTO(learning);
    }


    //for manager
    public void updateApprovalStatus(UUID id, String newStatus) {
        UserLearnings userLearning = userLearningsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User learning not found with id " + id));

        userLearning.setApprovalStatus(ApprovalStatus.valueOf(newStatus));
        userLearningsRepository.save(userLearning);

        userLearningsMapper.toUserLearningsDTO(userLearning);
    }
}
