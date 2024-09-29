package com.example.demo.services.UsersDB;

import com.example.demo.dtos.usersDB.DepartmentsDTO;
import com.example.demo.entities.UsersDB.Departments;
import com.example.demo.mappers.usersDB.DepartmentsMapper;
import com.example.demo.repositories.UsersDB.DepartmentsRepository;
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
public class DepartmentsServicesTests {

    @Mock
    private DepartmentsRepository departmentsRepository;

    @Mock
    private DepartmentsMapper departmentsMapper;

    @InjectMocks
    private DepartmentsServices departmentsServices;


    // Variables
    private DepartmentsDTO departmentsDTO1;
    private DepartmentsDTO departmentsDTO2;
    private Departments departments1;
    @BeforeEach
    public void setUp() {
        departmentsDTO1= new DepartmentsDTO("Software Engineering");
        departmentsDTO2 = new DepartmentsDTO("Quality Assurance");
        departments1 = new Departments("Software Engineering");
    }


    @Test
    public void DepartmentsService_GetDepartment_ReturnsDepartmentsDTO() {
        UUID uuid = UUID.randomUUID();

        when(departmentsRepository.findById(uuid)).thenReturn(Optional.of(departments1));
        when(departmentsMapper.toDepartmentsDTO(departments1)).thenReturn(departmentsDTO1);

        DepartmentsDTO returnedDepartmentsDTO = departmentsServices.getDepartment(uuid);

        verify(departmentsRepository, times(1)).findById(uuid);
        Assertions.assertEquals(returnedDepartmentsDTO, departmentsDTO1);
    }

    @Test
    public void DepartmentsService_GetDepartment_ThrowsEntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(departmentsRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> departmentsServices.getDepartment(uuid));

        verify(departmentsRepository, times(1)).findById(uuid);
    }

    @Test
    public void DepartmentsService_AddDepartments_ReturnsDepartmentsDTO() {

        when(departmentsMapper.toDepartments(any(DepartmentsDTO.class))).thenReturn(departments1);
        when(departmentsMapper.toDepartmentsDTO(any(Departments.class))).thenReturn(departmentsDTO1);

        DepartmentsDTO returnedDepartmentsDTO = departmentsServices.addDepartment(departmentsDTO1);

        verify(departmentsRepository, times(1)).save(departments1);
        Assertions.assertEquals(returnedDepartmentsDTO, departmentsDTO1);
    }

    @Test
    public void DepartmentsService_UpdateDepartments_ReturnsDepartmentsDTO() {

        UUID uuid = UUID.randomUUID();

        when(departmentsRepository.findById(any(UUID.class))).thenReturn(Optional.of(departments1));

        when(departmentsMapper.toDepartmentsDTO(any(Departments.class))).thenReturn(departmentsDTO1);
        DepartmentsDTO returnedDepartmentsDTO = departmentsServices.updateDepartment(uuid, departmentsDTO1);



        verify(departmentsMapper).updateDepartmentsFromDTO(any(DepartmentsDTO.class), eq(departments1));
        verify(departmentsRepository).save(departments1);
        Assertions.assertEquals(returnedDepartmentsDTO, departmentsDTO1);


    }

    @Test
    public void DepartmentsService_UpdateDepartments_ReturnsEntityNotFoundException() {

        UUID uuid = UUID.randomUUID();

        when(departmentsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()-> departmentsServices.updateDepartment(uuid, departmentsDTO1));

    }


    @Test
    public void DepartmentsService_DeleteDepartment_Success() {
        UUID uuid = UUID.randomUUID();

        when(departmentsRepository.existsById(uuid)).thenReturn(true);

        departmentsServices.deleteDepartment(uuid);

        verify(departmentsRepository, times(1)).deleteById(uuid);
    }

    @Test
    public void DepartmentsService_DeleteDepartment_ThrowsEntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(departmentsRepository.existsById(uuid)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> departmentsServices.deleteDepartment(uuid));

        verify(departmentsRepository, times(1)).existsById(uuid);
        verify(departmentsRepository, never()).deleteById(uuid);
    }


}
