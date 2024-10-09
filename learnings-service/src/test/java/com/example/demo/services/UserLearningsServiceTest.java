package com.example.demo.services;

import com.example.demo.dtos.SubmitUserLearningDTO;
import com.example.demo.dtos.UserLearningsDTO;
import com.example.demo.entities.*;
import com.example.demo.enums.ApprovalStatus;
import com.example.demo.mappers.UserLearningsMapper;
import com.example.demo.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserLearningsServiceTest {

    @Mock
    private UserLearningsRepository userLearningsRepository;

    @Mock
    private LearningsRepository learningsRepository;

    @Mock
    private LearningTypesRepository learningTypesRepository;

    @Mock
    private LearningSubjectsRepository learningSubjectsRepository;

    @Mock
    private ProofTypesRepository proofTypesRepository;


    @Mock
    private UserLearningsMapper userLearningsMapper;

    @InjectMocks
    private UserLearningsService userLearningsService;


    @Test
    public void submitUserLearning_learningExists_returnsSuccess() {
        UUID userId = UUID.randomUUID();
        UUID learningId = UUID.randomUUID();
        UUID proofTypeId = UUID.randomUUID();

        SubmitUserLearningDTO dto = new SubmitUserLearningDTO();
        dto.setUserId(userId);
        dto.setLearningId(learningId);
        dto.setProofTypeId(proofTypeId);
        dto.setProof("proof-example");
        dto.setApprovalStatus(ApprovalStatus.Pending);

        Learnings existingLearning = new Learnings();
        ProofTypes proofType = new ProofTypes();

        when(learningsRepository.findById(learningId)).thenReturn(Optional.of(existingLearning));
        when(proofTypesRepository.findById(proofTypeId)).thenReturn(Optional.of(proofType));

        userLearningsService.submitUserLearning(dto);

        verify(learningsRepository, times(1)).findById(learningId);
        verify(proofTypesRepository, times(1)).findById(proofTypeId);
        verify(userLearningsRepository, times(1)).save(any(UserLearnings.class));
    }

    @Test
    public void submitUserLearning_newLearning_returnsSuccess() {
        UUID userId = UUID.randomUUID();
        UUID proofTypeId = UUID.randomUUID();

        SubmitUserLearningDTO dto = new SubmitUserLearningDTO();
        dto.setUserId(userId);
        dto.setLearningTypeId(UUID.randomUUID());
        dto.setLearningSubjectId(UUID.randomUUID());
        dto.setProofTypeId(proofTypeId);
        dto.setProof("proof-example");
        dto.setApprovalStatus(ApprovalStatus.Pending);

        ProofTypes proofType = new ProofTypes();


        when(learningTypesRepository.findById(any(UUID.class))).thenReturn(Optional.of(new LearningTypes()));
        when(learningSubjectsRepository.findById(any(UUID.class))).thenReturn(Optional.of(new LearningSubjects()));
        when(proofTypesRepository.findById(proofTypeId)).thenReturn(Optional.of(proofType)); // Mocking proofTypesRepository

        userLearningsService.submitUserLearning(dto);

        verify(learningsRepository, times(1)).save(any(Learnings.class));
        verify(proofTypesRepository, times(1)).findById(proofTypeId);
        verify(userLearningsRepository, times(1)).save(any(UserLearnings.class));
    }

    @Test
    public void getSubmittedLearningByUser_learningExists_returnsSuccess() {
        UUID userId = UUID.randomUUID();
        UserLearnings userLearning = new UserLearnings();
        userLearning.setUserId(userId);
        when(userLearningsRepository.findByUserId(userId)).thenReturn(Arrays.asList(userLearning));
        when(userLearningsMapper.toUserLearningsDTO(any(UserLearnings.class))).thenReturn(new UserLearningsDTO());

        var result = userLearningsService.getSubmittedLearningsByUser(userId);

        assertNotNull(result);
        verify(userLearningsRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void getSubmittedLearningByID_learningExists_returnsSuccess() {
        UUID learningId = UUID.randomUUID();
        UserLearnings userLearning = new UserLearnings();
        when(userLearningsRepository.findById(learningId)).thenReturn(Optional.of(userLearning));
        when(userLearningsMapper.toUserLearningsDTO(any(UserLearnings.class))).thenReturn(new UserLearningsDTO());

        var result = userLearningsService.getSubmittedLearningById(learningId);

        assertNotNull(result);
        verify(userLearningsRepository, times(1)).findById(learningId);
    }

    @Test
    public void getSubmittedLearningByUser_learningNotExists_throwsException() {
        UUID learningId = UUID.randomUUID();
        when(userLearningsRepository.findById(learningId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userLearningsService.getSubmittedLearningById(learningId);
        });
    }

    @Test
    public void testUpdateApprovalStatus_returnsSuccess() {
        UUID learningId = UUID.randomUUID();
        UserLearnings userLearning = new UserLearnings();
        userLearning.setApprovalStatus(ApprovalStatus.Pending);
        when(userLearningsRepository.findById(learningId)).thenReturn(Optional.of(userLearning));

        userLearningsService.updateApprovalStatus(learningId, "Approved");

        verify(userLearningsRepository, times(1)).save(userLearning);
        assertEquals(ApprovalStatus.Approved, userLearning.getApprovalStatus());
    }

    @Test
    public void testUpdateApprovalStatus_LearningNotFound_throwsException() {

        UUID learningId = UUID.randomUUID();
        when(userLearningsRepository.findById(learningId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> {
            userLearningsService.updateApprovalStatus(learningId, "APPROVED");
        });
    }
}
