package com.example.demo.services.UsersDB;

import com.example.demo.dtos.usersDB.TitlesDTO;
import com.example.demo.entities.UsersDB.Departments;
import com.example.demo.entities.UsersDB.Titles;
import com.example.demo.mappers.usersDB.TitlesMapper;
import com.example.demo.repositories.UsersDB.DepartmentsRepository;
import com.example.demo.repositories.UsersDB.TitlesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TitlesServicesTests {

    @Mock
    private TitlesRepository titlesRepository;

    @Mock
    private DepartmentsRepository departmentsRepository;

    @Mock
    private TitlesMapper titlesMapper;

    @InjectMocks
    private TitlesServices titlesServices;

    private TitlesDTO titlesDTO;
    private Titles title;
    private Departments department;

    @BeforeEach
    public void setUp() {
        titlesDTO = new TitlesDTO();
        titlesDTO.setDepartmentId(UUID.randomUUID());

        title = new Titles();
        department = new Departments();
    }

    @Test
    public void TitlesService_GetTitle_ReturnsTitlesDTO() {
        UUID uuid = UUID.randomUUID();

        when(titlesRepository.findById(uuid)).thenReturn(Optional.of(title));
        when(titlesMapper.toTitlesDTO(any(Titles.class))).thenReturn(titlesDTO);

        TitlesDTO returnedTitlesDTO = titlesServices.getTitles(uuid);

        verify(titlesRepository, times(1)).findById(uuid);
        Assertions.assertEquals(returnedTitlesDTO, titlesDTO);
    }

    @Test
    public void TitlesService_GetTitle_ThrowsEntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(titlesRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> titlesServices.getTitles(uuid));

        verify(titlesRepository, times(1)).findById(uuid);
    }

    @Test
    public void TitlesService_AddTitle_ReturnsTitlesDTO() {
        when(titlesMapper.toTitle(any(TitlesDTO.class))).thenReturn(title);
        when(departmentsRepository.findById(any(UUID.class))).thenReturn(Optional.of(department));
        when(titlesMapper.toTitlesDTO(any(Titles.class))).thenReturn(titlesDTO);

        TitlesDTO returnedTitlesDTO = titlesServices.addTitles(titlesDTO);

        verify(titlesRepository, times(1)).save(title);
        Assertions.assertEquals(returnedTitlesDTO, titlesDTO);
    }

    @Test
    public void TitlesService_AddTitle_ThrowsEntityNotFoundException_WhenDepartmentNotFound() {
        when(departmentsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> titlesServices.addTitles(titlesDTO));

        verify(titlesRepository, never()).save(any(Titles.class));
    }

    @Test
    public void TitlesService_UpdateTitle_ReturnsTitlesDTO() {
        UUID uuid = UUID.randomUUID();

        when(titlesRepository.findById(uuid)).thenReturn(Optional.of(title));
        when(departmentsRepository.findById(any(UUID.class))).thenReturn(Optional.of(department));
        when(titlesMapper.toTitlesDTO(any(Titles.class))).thenReturn(titlesDTO);

        TitlesDTO returnedTitlesDTO = titlesServices.updateTitles(uuid, titlesDTO);

        verify(titlesMapper).updateTitlesFromDTO(titlesDTO, title);
        verify(titlesRepository, times(1)).save(title);
        Assertions.assertEquals(returnedTitlesDTO, titlesDTO);
    }

    @Test
    public void TitlesService_UpdateTitle_ThrowsEntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(titlesRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> titlesServices.updateTitles(uuid, titlesDTO));

        verify(titlesRepository, times(1)).findById(uuid);
        verify(titlesRepository, never()).save(any(Titles.class));
    }

    @Test
    public void TitlesService_DeleteTitle_Success() {
        UUID uuid = UUID.randomUUID();

        when(titlesRepository.existsById(uuid)).thenReturn(true);

        titlesServices.deleteTitles(uuid);

        verify(titlesRepository, times(1)).deleteById(uuid);
    }

    @Test
    public void TitlesService_DeleteTitle_ThrowsEntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(titlesRepository.existsById(uuid)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> titlesServices.deleteTitles(uuid));

        verify(titlesRepository, times(1)).existsById(uuid);
        verify(titlesRepository, never()).deleteById(uuid);
    }
}
