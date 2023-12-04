import lombok.SneakyThrows;
import org.example.util.Utils;
import org.example.util.dto.GetJobStatusResponse;
import org.example.util.dto.ScheduleJobRequest;
import org.example.util.dto.ScheduleJobResponse;
import org.example.util.enums.JobStatus;
import org.example.util.stubs.Stubs;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertNotNull;


public class JobSchedulerTests {
    Stubs stubs = Stubs.builder()
            .VALID_VALUE(VALID_VALUE)
            .INVALID_VALUE(INVALID_VALUE)
            .VALUE_WITH_ERROR(VALUE_WITH_ERROR)
            .VALUE_WITH_TIMEOUT(VALUE_WITH_TIMEOUT)
            .build();
    private static final String VALID_VALUE = "1";
    private static final String INVALID_VALUE = "2";
    private static final String VALUE_WITH_ERROR = "3";
    private static final String VALUE_WITH_TIMEOUT = "4";

    ScheduleJobRequest scheduleJobRequest;

    @BeforeClass
    public void setup() {
        stubs.init();
    }

    @AfterClass
    public void tearDown() {
        stubs.close();
    }

    @SneakyThrows
    @Test
    public void testValidEntity() {

        scheduleJobRequest = ScheduleJobRequest.builder()
                .id(VALID_VALUE)
                .build();

        stubs.createValidStubs();

        ScheduleJobResponse actualScheduleJobResponse = given()
                .contentType("application/json")
                .body(scheduleJobRequest.toJson())
                .when()
                .post("/scheduleJob")
                .then()
                .statusCode(200)
                .extract()
                .as(ScheduleJobResponse.class);

        String jobId = actualScheduleJobResponse.getJobId();
        assertNotNull(jobId, "Job ID should not be null");

        Utils.waitFor(120, () -> {
            GetJobStatusResponse statusResponse = given()
                    .when()
                    .get("/job/" + jobId)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(GetJobStatusResponse.class);
            Assert.assertTrue(statusResponse.getJobStatus().equals(JobStatus.SUCCESS) ||
                            statusResponse.getJobStatus().equals(JobStatus.IN_PROGRESS),
                    "Status should be IN PROGRESS or SUCCESS");
            return statusResponse.getJobStatus().equals(JobStatus.SUCCESS);
        }, 5, "Job did not complete successfully within the expected time");

        Assert.assertTrue(stubs.wireMockServer
                .findAll(getRequestedFor(urlEqualTo("/job/" + jobId))).size() > 1);
    }

    @SneakyThrows
    @Test
    public void testInvalidEntity() {

        scheduleJobRequest = ScheduleJobRequest.builder()
                .id(INVALID_VALUE)
                .build();
        stubs.createInvalidStubs();

        ScheduleJobResponse actualScheduleJobResponse = given()
                .contentType("application/json")
                .body(scheduleJobRequest.toJson())
                .when()
                .post("/scheduleJob")
                .then()
                .statusCode(200)
                .extract()
                .as(ScheduleJobResponse.class);

        String jobId = actualScheduleJobResponse.getJobId();
        assertNotNull(jobId, "Job ID should not be null");

        Utils.waitFor(120, () -> {
            GetJobStatusResponse statusResponse = given()
                    .when()
                    .get("/job/" + jobId)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(GetJobStatusResponse.class);
            Assert.assertEquals(JobStatus.ERROR, statusResponse.getJobStatus(), "Status should be ERROR");
            return statusResponse.getJobStatus().equals(JobStatus.ERROR);
        }, 5, "Job did not get error status for invalid entity.");

        stubs.wireMockServer.verify(1, getRequestedFor(urlEqualTo("/job/" + jobId)));
    }

    @SneakyThrows
    @Test
    public void testErrorEntity() {

        scheduleJobRequest = ScheduleJobRequest.builder()
                .id(VALUE_WITH_ERROR)
                .build();

        stubs.createErrorStubs();

        ScheduleJobResponse actualScheduleJobResponse = given()
                .contentType("application/json")
                .body(scheduleJobRequest.toJson())
                .when()
                .post("/scheduleJob")
                .then()
                .statusCode(200)
                .extract()
                .as(ScheduleJobResponse.class);

        String jobId = actualScheduleJobResponse.getJobId();
        assertNotNull(jobId, "Job ID should not be null");

        Utils.waitFor(120, () -> {
            GetJobStatusResponse statusResponse = given()
                    .when()
                    .get("/job/" + jobId)
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(GetJobStatusResponse.class);
            Assert.assertTrue(statusResponse.getJobStatus().equals(JobStatus.ERROR) ||
                            statusResponse.getJobStatus().equals(JobStatus.IN_PROGRESS),
                    "Status should be IN PROGRESS or ERROR");
            return statusResponse.getJobStatus().equals(JobStatus.ERROR);
        }, 5, "Job did not get error status for entity with error");

        Assert.assertTrue(stubs.wireMockServer
                .findAll(getRequestedFor(urlEqualTo("/job/" + jobId))).size() > 1);

    }

    @SneakyThrows
    @Test
    public void testTimeOutEntity() {

        scheduleJobRequest = ScheduleJobRequest.builder()
                .id(VALUE_WITH_TIMEOUT)
                .build();
        stubs.createTimeOutStubs();

        ScheduleJobResponse actualScheduleJobResponse = given()
                .contentType("application/json")
                .body(scheduleJobRequest.toJson())
                .when()
                .post("/scheduleJob")
                .then()
                .statusCode(200)
                .extract()
                .as(ScheduleJobResponse.class);

        String jobId = actualScheduleJobResponse.getJobId();
        assertNotNull(jobId, "Job ID should not be null");

        try {
            Utils.waitFor(120, () -> {
                GetJobStatusResponse statusResponse = given()
                        .when()
                        .get("/job/" + jobId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(GetJobStatusResponse.class);

                return statusResponse.getJobStatus().equals(JobStatus.SUCCESS);
            }, 5, "Job did not complete successfully within the expected time");
        } catch (TimeoutException e) {
            Assert.assertEquals(e.getMessage(), "Job did not complete successfully within the expected time");
        }
    }
}
