package org.example.util;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Utils {

    @SneakyThrows
    public static void waitFor(long timeoutSec, Callable<Boolean> action, long pollInterval, String message) throws InterruptedException, TimeoutException {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TimeUnit.SECONDS.toMillis(timeoutSec);

        while (System.currentTimeMillis() < endTime) {
            if (action.call()) {
                return;
            }
            Thread.sleep(pollInterval * 1000);
        }
        throw new TimeoutException(message);
    }
}
