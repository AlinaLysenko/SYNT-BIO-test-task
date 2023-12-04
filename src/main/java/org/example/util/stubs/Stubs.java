package org.example.util.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.Builder;
import lombok.SneakyThrows;
import org.example.util.dto.GetJobStatusResponse;
import org.example.util.dto.ScheduleJobRequest;
import org.example.util.dto.ScheduleJobResponse;
import org.example.util.enums.JobStatus;
import org.example.util.stubs.transformers.ErrorTransformer;
import org.example.util.stubs.transformers.SuccessTransformer;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Builder
public class Stubs {
    private final String VALID_VALUE;
    private final String INVALID_VALUE;
    private final String VALUE_WITH_ERROR;
    private final String VALUE_WITH_TIMEOUT;

    ScheduleJobRequest scheduleJobRequest;
    ScheduleJobResponse scheduleJobResponse;
    GetJobStatusResponse getJobStatusResponse;

    public WireMockServer wireMockServer;

    public void init() {
        wireMockServer = new WireMockServer(wireMockConfig()
                .port(8080)
                .extensions(new SuccessTransformer(), new ErrorTransformer()));
        wireMockServer.start();
        configureFor("localhost", 8080);
    }

    @SneakyThrows
    public void createValidStubs() {
        initJsons(VALID_VALUE, JobStatus.SUCCESS);
        createStubForScheduler();

        stubFor(get(urlPathMatching("/job/" + VALID_VALUE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withTransformers("success-transformer")));
    }

    @SneakyThrows
    public void createInvalidStubs() {
        initJsons(INVALID_VALUE, JobStatus.ERROR);
        createStubForScheduler();
        stubFor(get(urlEqualTo("/job/" + INVALID_VALUE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getJobStatusResponse.toJson())));
    }

    @SneakyThrows
    public void createErrorStubs() {
        initJsons(VALUE_WITH_ERROR, JobStatus.SUCCESS);
        createStubForScheduler();

        stubFor(get(urlPathMatching("/job/" + VALUE_WITH_ERROR))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withTransformers("error-transformer")));
    }

    @SneakyThrows
    public void createTimeOutStubs() {
        initJsons(VALUE_WITH_TIMEOUT, JobStatus.IN_PROGRESS);
        createStubForScheduler();
        stubFor(get(urlEqualTo("/job/" + VALUE_WITH_TIMEOUT))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getJobStatusResponse.toJson())));
    }

    @SneakyThrows
    private void createStubForScheduler() {
        stubFor(post(urlEqualTo("/scheduleJob"))
                .withRequestBody(equalToJson(scheduleJobRequest.toJson()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(scheduleJobResponse.toJson())));
    }

    private void initJsons(String value, JobStatus jobStatus) {
        scheduleJobRequest = ScheduleJobRequest.builder()
                .id(value)
                .build();

        scheduleJobResponse = ScheduleJobResponse.builder()
                .jobId(value)
                .build();

        getJobStatusResponse = jobStatus.createResponse();
    }

    public void close() {
        wireMockServer.stop();
    }
}
