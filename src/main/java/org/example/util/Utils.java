package org.example.util;

import lombok.SneakyThrows;

import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class Utils {

    @SneakyThrows
    public static void waitFor(long timeoutSec, Callable<Boolean> action, long pollInterval, String message) throws TimeoutException {
        LocalTime endTime = LocalTime.now().plusSeconds(timeoutSec);

        while (LocalTime.now().isBefore(endTime)) {
            if (action.call()) {
                return;
            }
            Thread.sleep(pollInterval * 1000);
        }
        throw new TimeoutException(message);
    }
}
