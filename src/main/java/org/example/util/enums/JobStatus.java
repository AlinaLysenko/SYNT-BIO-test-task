package org.example.util.enums;

import org.example.util.dto.GetJobStatusResponse;

public enum JobStatus {
    IN_PROGRESS,
    SUCCESS,
    ERROR;

    public GetJobStatusResponse createResponse() {
        return GetJobStatusResponse.builder()
                .jobStatus(this)
                .build();
    }
}
