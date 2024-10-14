package com.example.demo.controllers;


import com.example.demo.dtos.RequestSubmitCPDto;
import com.example.demo.dtos.RequestSubmitCPwithID;
import com.example.demo.dtos.SubmittedCPDto;
import com.example.demo.service.SubmittedCPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/submittedCP")
public class SubmittedCPController {

    private final SubmittedCPService submittedCPService;

    public SubmittedCPController(SubmittedCPService submittedCPService) {
        this.submittedCPService = submittedCPService;
    }

    @PostMapping("/add")
    public ResponseEntity<SubmittedCPDto> addSubmittedCP(RequestSubmitCPDto requestSubmitCP) {
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
}
