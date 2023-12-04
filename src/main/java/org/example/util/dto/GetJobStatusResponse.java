package org.example.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.example.util.enums.JobStatus;

@Data
@Builder
public class GetJobStatusResponse {
    @JsonProperty("jobStatus")
    private JobStatus jobStatus;

    @Tolerate
    public GetJobStatusResponse() {
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
