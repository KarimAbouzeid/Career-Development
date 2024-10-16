package com.example.demo.services;

import com.example.demo.dtos.ReceivedNotificationDTO;
import com.example.demo.dtos.SubmitUserLearningDTO;
import com.example.demo.dtos.UserLearningResponseDTO;
import com.example.demo.dtos.UserLearningsDTO;
import com.example.demo.entities.*;
import com.example.demo.enums.ApprovalStatus;
import com.example.demo.enums.EntityType;
import com.example.demo.kafka.KafkaProducerService;
import com.example.demo.mappers.UserLearningsMapper;
import com.example.demo.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    private final KafkaProducerService kafkaProducerService;

    private final UserService userService;


    @Value("notification")
    private String approvalNotificationTopic;

    @Autowired
    public UserLearningsService(UserLearningsRepository userLearningsRepository, LearningsRepository learningsRepository, LearningTypesRepository learningTypesRepository, LearningSubjectsRepository learningSubjectsRepository, ProofTypesRepository proofTypesRepository, BoostersRepository boostersRepository, UserLearningsMapper userLearningsMapper, KafkaProducerService kafkaProducerService, UserService userService) {
        this.userLearningsRepository = userLearningsRepository;
        this.learningsRepository = learningsRepository;
        this.learningTypesRepository = learningTypesRepository;
        this.learningSubjectsRepository = learningSubjectsRepository;
        this.proofTypesRepository = proofTypesRepository;
        this.boostersRepository = boostersRepository;
        this.userLearningsMapper = userLearningsMapper;
        this.kafkaProducerService = kafkaProducerService;
        this.userService = userService;
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

        System.out.println(dto);
        System.out.println(dto.getUserId());


        UUID managerId = userService.getManager(dto.getUserId());

        ReceivedNotificationDTO notification = new ReceivedNotificationDTO(
                "New learning submitted by employee.",
                new Date(),
                EntityType.User,
                List.of(managerId)

        );



        kafkaProducerService.sendNotification(approvalNotificationTopic,notification);

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
                userLearning.getId(),
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

        ReceivedNotificationDTO notification = new ReceivedNotificationDTO(
                "Learning status updated by manager.",
                new Date(),
                EntityType.Manager,
                List.of(userLearning.getUserId())
        );

        kafkaProducerService.sendNotification(approvalNotificationTopic,notification);
    }

    public void updateComment(UUID id, String comment) {
        UserLearnings userLearning = userLearningsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User learning not found with id " + id));

        userLearning.setComment(comment);
        userLearningsRepository.save(userLearning);

        userLearningsMapper.toUserLearningsDTO(userLearning);

        ReceivedNotificationDTO notification = new ReceivedNotificationDTO(
                "Learning comment updated by manager.",
                new Date(), EntityType.Manager,
                List.of(userLearning.getUserId())
                );

        kafkaProducerService.sendNotification(approvalNotificationTopic,notification);
    }
}
