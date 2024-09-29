package com.example.demo.controllers.usersDB;

import com.example.demo.dtos.usersDB.DepartmentsDTO;
import com.example.demo.services.UsersDB.DepartmentsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
public class DepartmentsController {

    private final DepartmentsServices departmentsServices;

    @Autowired
    public DepartmentsController(DepartmentsServices departmentsServices) {
        this.departmentsServices = departmentsServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsDTO> getDepartment(@PathVariable UUID id) {

            DepartmentsDTO departmentDTO = departmentsServices.getDepartment(id);
            return ResponseEntity.ok(departmentDTO);
    }

    @PostMapping
    public ResponseEntity<DepartmentsDTO> addDepartment(@RequestBody DepartmentsDTO departmentsDTO) {

            DepartmentsDTO newDepartment = departmentsServices.addDepartment(departmentsDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentsDTO> updateDepartment(@PathVariable UUID id, @RequestBody DepartmentsDTO departmentsDTO) {

            DepartmentsDTO updatedDepartment = departmentsServices.updateDepartment(id, departmentsDTO);
            return ResponseEntity.ok(updatedDepartment);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable UUID id) {
            departmentsServices.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully");

    }
}
