package com.example.demo.services;


import com.example.demo.dtos.TitlesDTO;
import com.example.demo.entities.Departments;
import com.example.demo.entities.Titles;
import com.example.demo.mappers.TitlesMapper;
import com.example.demo.repositories.DepartmentsRepository;
import com.example.demo.repositories.TitlesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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


    @Test
    public void getTitlesByDepartment_success_ReturnsTitlesList() {
        UUID departmentId = UUID.randomUUID();
        Departments department = new Departments(departmentId, "IT", null);

        Titles title1 = new Titles(UUID.randomUUID(), "Software Engineer", false, null, department);
        Titles title2 = new Titles(UUID.randomUUID(), "Project Manager", true, null, department);
        List<Titles> titlesList = Arrays.asList(title1, title2);

        TitlesDTO titleDTO1 = new TitlesDTO(UUID.randomUUID(),title1.getTitle(), title1.getIsManager(), departmentId);
        TitlesDTO titleDTO2 = new TitlesDTO(UUID.randomUUID(),title2.getTitle(), title2.getIsManager(), departmentId);

        when(departmentsRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(titlesRepository.findByDepartmentId(department)).thenReturn(titlesList);
        when(titlesMapper.toTitlesDTO(title1)).thenReturn(titleDTO1);
        when(titlesMapper.toTitlesDTO(title2)).thenReturn(titleDTO2);

        List<TitlesDTO> result = titlesServices.getTitlesByDepartment(departmentId);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Software Engineer", result.get(0).getTitle());
        Assertions.assertEquals("Project Manager", result.get(1).getTitle());

        verify(departmentsRepository, times(1)).findById(departmentId);
        verify(titlesRepository, times(1)).findByDepartmentId(department);
        verify(titlesMapper, times(1)).toTitlesDTO(title1);
        verify(titlesMapper, times(1)).toTitlesDTO(title2);
    }

    @Test
    public void getTitlesByDepartment_departmentNotFound_ThrowsEntityNotFoundException() {
        UUID departmentId = UUID.randomUUID();

        when(departmentsRepository.findById(departmentId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            titlesServices.getTitlesByDepartment(departmentId);
        });

        Assertions.assertEquals("Department with id " + departmentId + " not found", thrown.getMessage());
        verify(departmentsRepository, times(1)).findById(departmentId);
        verify(titlesRepository, never()).findByDepartmentId(any());
    }

    @Test
    public void getTitlesByDepartment_noTitlesFound_ReturnsEmptyList() {

        UUID departmentId = UUID.randomUUID();
        Departments department = new Departments(departmentId, "HR", null);

        when(departmentsRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(titlesRepository.findByDepartmentId(department)).thenReturn(new ArrayList<>());

        List<TitlesDTO> result = titlesServices.getTitlesByDepartment(departmentId);

        Assertions.assertTrue(result.isEmpty());

        verify(departmentsRepository, times(1)).findById(departmentId);
        verify(titlesRepository, times(1)).findByDepartmentId(department);
    }
}
