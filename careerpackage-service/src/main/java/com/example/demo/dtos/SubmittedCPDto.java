    package com.example.demo.dtos;

    import com.example.demo.enums.Status;
    import com.example.demo.enums.Title;
    import jakarta.persistence.EnumType;
    import jakarta.persistence.Enumerated;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.UUID;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SubmittedCPDto {

        private UUID submissionId;

        private UUID careerPackageId;

        private UUID userId;

        private UUID managerId;

        @Enumerated(EnumType.STRING)
        private Title title;

        private String googleDocLink;

        private Status status;



    }
