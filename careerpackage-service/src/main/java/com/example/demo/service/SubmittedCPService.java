package com.example.demo.service;


import com.example.demo.dtos.CareerPackageResponseDto;
import com.example.demo.dtos.RequestSubmitCPDto;
import com.example.demo.dtos.RequestSubmitCPwithID;
import com.example.demo.dtos.SubmittedCPDto;
import com.example.demo.entities.SubmittedCP;
import com.example.demo.enums.Status;
import com.example.demo.mappers.SubmittedCPMapper;
import com.example.demo.repository.SubmittedCPRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubmittedCPService {




    @Value("${userManagementService.url}")
    private String userManagementServiceUrl;

    @Value("${careerPackageService.url}")
    private String careerPackageServiceUrl;




    private final SubmittedCPRepository submittedCPRepository;
    private final SubmittedCPMapper submittedCPMapper;
    private final RestTemplate restTemplate;


    public SubmittedCPService(SubmittedCPRepository submittedCPRepository, SubmittedCPMapper submittedCPMapper, RestTemplate restTemplate) {
        this.submittedCPRepository = submittedCPRepository;
        this.submittedCPMapper = submittedCPMapper;
        this.restTemplate = restTemplate;
    }


    public SubmittedCPDto addSubmittedCP(RequestSubmitCPDto requestSubmitCPDto) {

        String urlGetManager = userManagementServiceUrl + "/getManager/" + requestSubmitCPDto.getUserId();
        UUID managerId = restTemplate.getForObject(urlGetManager, UUID.class);


        String urlGetCareerPackage = careerPackageServiceUrl + "/getCareerPackageByTitle/" + requestSubmitCPDto.getTitle();
        UUID careerPackageId = restTemplate.getForObject(urlGetCareerPackage, UUID.class);


        SubmittedCPDto submittedCPDto = new SubmittedCPDto();
        submittedCPDto.setCareerPackageId(careerPackageId);
        submittedCPDto.setUserId(requestSubmitCPDto.getUserId());
        submittedCPDto.setManagerId(managerId);
        submittedCPDto.setGoogleDocLink(requestSubmitCPDto.getGoogleDocLink());
        submittedCPDto.setStatus(Status.PENDING);

        SubmittedCP submittedCP = submittedCPMapper.toSubmittedCP(submittedCPDto);
        submittedCP = submittedCPRepository.save(submittedCP);
        return submittedCPMapper.toSubmittedCPDto(submittedCP);
    }

    public SubmittedCPDto updateSubmittedCP(SubmittedCPDto submittedCPDto) {
        SubmittedCP submittedCP = submittedCPRepository.findById(submittedCPDto.getSubmissionId()).orElseThrow(() -> new EntityNotFoundException("Submitted CP with id " + submittedCPDto.getSubmissionId() + " not found"));

        submittedCPMapper.updateSubmittedCareerPackageFromDto(submittedCPDto, submittedCP);
        submittedCP = submittedCPRepository.save(submittedCP);
        return submittedCPMapper.toSubmittedCPDto(submittedCP);
    }


    public SubmittedCPDto getSubmittedCP(UUID id) {
        SubmittedCP submittedCP = submittedCPRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Submitted CP with id " + id + " not found"));
        return submittedCPMapper.toSubmittedCPDto(submittedCP);
    }



    public void deleteSubmittedCP (SubmittedCPDto submittedCPDto) {

        submittedCPRepository.deleteById(submittedCPDto.getSubmissionId());

    }

    public Page<SubmittedCP>  getCareerPackagePaginatedByUser(UUID userId, Pageable pageable) {

        Page<SubmittedCP> submittedCPPage = submittedCPRepository.findSubmittedCPByUserId(userId, pageable);
        System.out.println(submittedCPPage);
        if(submittedCPPage.isEmpty()) {
            return Page.empty();
        }
        return submittedCPPage;


    }



}
