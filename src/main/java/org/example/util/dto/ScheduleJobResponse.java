package org.example.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Builder;
import lombok.experimental.Tolerate;

@Data
@Builder
public class ScheduleJobResponse {
    @JsonProperty("jobId")
    private String jobId;

    @Tolerate
    public ScheduleJobResponse() {
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
