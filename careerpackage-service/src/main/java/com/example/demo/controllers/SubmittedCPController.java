package com.example.demo.controllers;


import com.example.demo.dtos.RequestSubmitCPDto;
import com.example.demo.dtos.RequestSubmitCPwithID;
import com.example.demo.dtos.SubmittedCPDto;
import com.example.demo.entities.SubmittedCP;
import com.example.demo.service.SubmittedCPService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/submittedCP")
public class SubmittedCPController {

    private final SubmittedCPService submittedCPService;

    public SubmittedCPController(SubmittedCPService submittedCPService) {
        this.submittedCPService = submittedCPService;
    }

    @PostMapping("/add")
    public ResponseEntity<SubmittedCPDto> addSubmittedCP(@RequestBody RequestSubmitCPDto requestSubmitCP) {
        SubmittedCPDto createdSubmittedCP = submittedCPService.addSubmittedCP(requestSubmitCP);
        return ResponseEntity.ok(createdSubmittedCP);
    }

    @PostMapping("/update")
    public ResponseEntity<SubmittedCPDto> updateSubmittedCP(SubmittedCPDto submittedCPDto) {
        SubmittedCPDto updatedSubmittedCP = submittedCPService.updateSubmittedCP(submittedCPDto);
        return ResponseEntity.ok(updatedSubmittedCP);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteSubmittedCP(SubmittedCPDto submittedCPDto) {
        submittedCPService.deleteSubmittedCP(submittedCPDto);
        return ResponseEntity.ok("Submitted CP with submission id " + submittedCPDto.getSubmissionId() + " has been deleted successfully");
    }

    @GetMapping("/getCareerPackagePaginatedByUser/{userId}")
    public ResponseEntity<Page<SubmittedCP>> getCareerPackagePaginatedByUser(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @PathVariable UUID userId)
    {

        Pageable pageable = PageRequest.of(page, size);
        Page<SubmittedCP> submittedCP = submittedCPService.getCareerPackagePaginatedByUser(userId, pageable);
        return ResponseEntity.ok(submittedCP);
    }

}
