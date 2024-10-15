package com.example.demo.services;

import com.example.demo.dtos.SubmitUserLearningDTO;
import com.example.demo.dtos.UserLearningResponseDTO;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
    private BoostersRepository boostersRepository;

    @Mock
    private UserLearningsMapper userLearningsMapper;

    @InjectMocks
    private UserLearningsService userLearningsService;

    private UUID learningTypeId;
    private UUID learningSubjectId;
    private UUID proofTypeId;
    private UUID boosterId;
    private UUID userId;
    private SubmitUserLearningDTO submitUserLearningDTO;
    private UserLearnings userLearning;

    @BeforeEach
    public void setUp() {
        learningTypeId = UUID.randomUUID();
        learningSubjectId = UUID.randomUUID();
        proofTypeId = UUID.randomUUID();
        boosterId = UUID.randomUUID();
        userId = UUID.randomUUID();

        submitUserLearningDTO = new SubmitUserLearningDTO();
        submitUserLearningDTO.setUserId(userId);
        submitUserLearningDTO.setLearningTypeId(learningTypeId);
        submitUserLearningDTO.setLearningSubjectId(learningSubjectId);
        submitUserLearningDTO.setProofTypeId(proofTypeId);
        submitUserLearningDTO.setActiveBoosterId(boosterId);
        submitUserLearningDTO.setDate(new Date());
        submitUserLearningDTO.setApprovalStatus(ApprovalStatus.Pending);
        submitUserLearningDTO.setComment("This is a comment");

        userLearning = new UserLearnings();
        userLearning.setId(UUID.randomUUID());
        userLearning.setUserId(userId);
    }

    @Test
    public void submitUserLearning_existingLearningId_submitsSuccessfully() {
        UUID learningId = UUID.randomUUID();
        UUID proofTypeId = UUID.randomUUID();

        submitUserLearningDTO.setLearningId(learningId);
        submitUserLearningDTO.setProofTypeId(proofTypeId);
        submitUserLearningDTO.setActiveBoosterId(null);

        Learnings existingLearning = new Learnings();
        existingLearning.setId(learningId);
        existingLearning.setTitle("Existing Learning Title");

        when(learningsRepository.findById(learningId)).thenReturn(Optional.of(existingLearning));

        ProofTypes existingProofType = new ProofTypes();
        existingProofType.setId(proofTypeId);
        existingProofType.setName("Existing Proof Type");

        when(proofTypesRepository.findById(proofTypeId)).thenReturn(Optional.of(existingProofType));

        when(userLearningsMapper.toUserLearningsDTO(any())).thenReturn(new UserLearningsDTO());

        userLearningsService.submitUserLearning(submitUserLearningDTO);

        verify(userLearningsRepository, times(1)).save(any(UserLearnings.class));

        verify(userLearningsRepository).save(argThat(savedLearning ->
                savedLearning.getLearning() != null &&
                        savedLearning.getLearning().getId().equals(learningId) &&
                        savedLearning.getProofType() != null &&
                        savedLearning.getProofType().getId().equals(proofTypeId)
        ));
    }


    @Test
    public void submitUserLearning_newLearning_createsAndSubmitsSuccessfully() {
        // Arrange
        submitUserLearningDTO.setLearningId(null); // No existing learning, should create a new one

        when(learningTypesRepository.findById(learningTypeId)).thenReturn(Optional.of(new LearningTypes()));
        when(learningSubjectsRepository.findById(learningSubjectId)).thenReturn(Optional.of(new LearningSubjects()));
        when(proofTypesRepository.findById(proofTypeId)).thenReturn(Optional.of(new ProofTypes()));
        when(boostersRepository.findById(boosterId)).thenReturn(Optional.of(new Boosters()));
        when(userLearningsMapper.toUserLearningsDTO(any())).thenReturn(new UserLearningsDTO());

        // Act
        userLearningsService.submitUserLearning(submitUserLearningDTO);

        // Assert
        verify(userLearningsRepository, times(1)).save(any(UserLearnings.class));
    }

    @Test
    public void submitUserLearning_learningNotFound_throwsEntityNotFoundException() {
        // Arrange
        UUID learningId = UUID.randomUUID();
        submitUserLearningDTO.setLearningId(learningId);

        when(learningsRepository.findById(learningId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userLearningsService.submitUserLearning(submitUserLearningDTO));
    }

    @Test
    public void getSubmittedLearningsByUser_userHasLearnings_returnsList() {
        // Arrange
        when(userLearningsRepository.findByUserId(userId)).thenReturn(Collections.singletonList(userLearning));
        when(userLearningsMapper.toUserLearningsDTO(userLearning)).thenReturn(new UserLearningsDTO());

        // Act
        List<UserLearningsDTO> result = userLearningsService.getSubmittedLearningsByUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void getSubmittedLearningsByUser_userHasNoLearnings_returnsEmptyList() {
        // Arrange
        when(userLearningsRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        // Act
        List<UserLearningsDTO> result = userLearningsService.getSubmittedLearningsByUser(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getSubmittedLearningById_learningExists_returnsDTO() {
        // Arrange
        when(userLearningsRepository.findById(any())).thenReturn(Optional.of(userLearning));
        when(userLearningsMapper.toUserLearningsDTO(userLearning)).thenReturn(new UserLearningsDTO());

        // Act
        UserLearningsDTO result = userLearningsService.getSubmittedLearningById(userLearning.getId());

        // Assert
        assertNotNull(result);
    }

    @Test
    public void getSubmittedLearningById_learningNotFound_throwsEntityNotFoundException() {
        // Arrange
        when(userLearningsRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userLearningsService.getSubmittedLearningById(userLearning.getId()));
    }

    @Test
    public void updateApprovalStatus_learningExists_updatesSuccessfully() {
        // Arrange
        when(userLearningsRepository.findById(any())).thenReturn(Optional.of(userLearning));

        // Act
        userLearningsService.updateApprovalStatus(userLearning.getId(), ApprovalStatus.Approved.toString());

        // Assert
        verify(userLearningsRepository, times(1)).save(userLearning);
        assertEquals(ApprovalStatus.Approved, userLearning.getApprovalStatus());
    }

    @Test
    public void updateApprovalStatus_learningNotFound_throwsEntityNotFoundException() {
        // Arrange
        when(userLearningsRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userLearningsService.updateApprovalStatus(userLearning.getId(), ApprovalStatus.Approved.toString()));
    }

    @Test
    public void updateComment_learningExists_updatesSuccessfully() {
        // Arrange
        String newComment = "Updated comment";
        when(userLearningsRepository.findById(any())).thenReturn(Optional.of(userLearning));

        // Act
        userLearningsService.updateComment(userLearning.getId(), newComment);

        // Assert
        verify(userLearningsRepository, times(1)).save(userLearning);
        assertEquals(newComment, userLearning.getComment());
    }

    @Test
    public void updateComment_learningNotFound_throwsEntityNotFoundException() {
        // Arrange
        when(userLearningsRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userLearningsService.updateComment(userLearning.getId(), "Some comment"));
    }

    @Test
    public void getSubmittedLearningsDetails_validUserId_returnsListOfUserLearningResponseDTO() {
        UUID userId = UUID.randomUUID();

        Learnings mockLearning = mock(Learnings.class);
        when(mockLearning.getTitle()).thenReturn("Learning Title");
        when(mockLearning.getURL()).thenReturn("http://example.com");
        when(mockLearning.getLengthInHours()).thenReturn(2F);

        LearningTypes mockLearningType = mock(LearningTypes.class);
        when(mockLearningType.getBaseScore()).thenReturn(10);
        when(mockLearning.getLearningType()).thenReturn(mockLearningType);

        ProofTypes mockProofType = mock(ProofTypes.class);
        when(mockProofType.getName()).thenReturn("Proof Type");

        UserLearnings mockUserLearning = mock(UserLearnings.class);
        when(mockUserLearning.getId()).thenReturn(UUID.randomUUID());
        when(mockUserLearning.getLearning()).thenReturn(mockLearning);
        when(mockUserLearning.getProof()).thenReturn("Proof");
        when(mockUserLearning.getProofType()).thenReturn(mockProofType);
        when(mockUserLearning.getDate()).thenReturn(new Date());
        when(mockUserLearning.getApprovalStatus()).thenReturn(ApprovalStatus.Approved);
        when(mockUserLearning.getComment()).thenReturn("Comment");

        when(userLearningsRepository.findByUserId(userId)).thenReturn(Collections.singletonList(mockUserLearning));

        List<UserLearningResponseDTO> result = userLearningsService.getSubmittedLearningsDetails(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        UserLearningResponseDTO dto = result.get(0);
        assertEquals(mockUserLearning.getId(), dto.getId());
        assertEquals("Learning Title", dto.getTitle());
        assertEquals("http://example.com", dto.getURL());
        assertEquals("Proof", dto.getProof());
        assertEquals(mockUserLearning.getDate(), dto.getDate());
        assertEquals(ApprovalStatus.Approved, dto.getApprovalStatus());
        assertEquals("Comment", dto.getComment());
        assertEquals(2F, dto.getLengthInHours());
        assertEquals(10, dto.getBaseScore());
    }


    @Test
    public void getSubmittedLearningsDetails_noLearnings_returnsEmptyList() {
        // Arrange
        when(userLearningsRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        // Act
        List<UserLearningResponseDTO> result = userLearningsService.getSubmittedLearningsDetails(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
