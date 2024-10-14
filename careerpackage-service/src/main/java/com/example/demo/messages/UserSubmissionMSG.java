package com.example.demo.messages;


import lombok.Data;

import java.util.UUID;

@Data
public class UserSubmissionMSG {

    private UUID submissionId;

    private UUID userId;
}
