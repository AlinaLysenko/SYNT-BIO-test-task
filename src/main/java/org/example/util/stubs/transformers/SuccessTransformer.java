package org.example.util.stubs.transformers;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import lombok.SneakyThrows;
import org.example.util.enums.JobStatus;

import java.time.Duration;
import java.time.LocalTime;

public class SuccessTransformer extends ResponseTransformer {
    private final LocalTime startTime;
    private final Duration duration;

    public SuccessTransformer() {
        this.startTime = LocalTime.now();
        this.duration = Duration.ofMinutes(2);
    }

    @SneakyThrows
    @Override
    public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
        LocalTime now = LocalTime.now();
        String newBody = Duration.between(startTime, now).compareTo(duration) < 0 ?
                JobStatus.IN_PROGRESS.createResponse().toJson() :
                JobStatus.SUCCESS.createResponse().toJson();

        return Response.Builder.like(response).but().body(newBody).build();
    }

    @Override
    public String getName() {
        return "success-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
