package com.example.taskservice.dao.report;

import com.example.taskservice.domain.Status;
import com.example.taskservice.model.Task;
import com.example.taskservice.support.LocalTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class AvgTimeToCompletionReportServiceTest extends LocalTestSupport {

    @AfterTestClass
    void reset() {
        Mockito.reset(taskRepo);
    }

    @Test
    void avgTimeCounts() {
        List<Task> tasks = buildTasks();
        tasks.get(0).setStatus(Status.DONE);
        tasks.get(1).setStatus(Status.DONE);
        doReturn(tasks).when(taskRepo).findByPeriod(any(), any());
        avgTimeToCompletionReportService.setTaskRepo(taskRepo);

        Map<String, Object> reportData = avgTimeToCompletionReportService.buildReportData(Instant.EPOCH, Instant.MAX, Collections.emptyMap());
        assertFalse(reportData.isEmpty());
        assertData(reportData, 3, 2, 3.0);
    }

    @Test
    void avgTimeNanWhenNoCompletedTasks() {
        doReturn(buildTasks()).when(taskRepo).findByPeriod(any(), any());
        avgTimeToCompletionReportService.setTaskRepo(taskRepo);

        Map<String, Object> reportData = avgTimeToCompletionReportService.buildReportData(Instant.EPOCH, Instant.MAX, Collections.emptyMap());
        assertFalse(reportData.isEmpty());
        assertData(reportData, 3, 0, Double.NaN);
    }

    @Test
    void avgTimeNanWhenNoTasks() {
        doReturn(emptyList()).when(taskRepo).findByPeriod(any(), any());
        avgTimeToCompletionReportService.setTaskRepo(taskRepo);

        Map<String, Object> reportData = avgTimeToCompletionReportService.buildReportData(Instant.EPOCH, Instant.MAX, Collections.emptyMap());
        assertFalse(reportData.isEmpty());
        assertData(reportData, 0, 0, Double.NaN);
    }

    List<Task> buildTasks() {
        return Arrays.asList(
                Task.builder().createdAt(Instant.EPOCH).modifiedAt(Instant.EPOCH.plus(Duration.ofDays(4))).status(Status.TO_DO).build(),
                Task.builder().createdAt(Instant.EPOCH).modifiedAt(Instant.EPOCH.plus(Duration.ofDays(2))).status(Status.TO_DO).build(),
                Task.builder().createdAt(Instant.EPOCH).modifiedAt(Instant.EPOCH.plus(Duration.ofDays(100))).status(Status.TO_DO).build()
        );
    }

    private static void assertData(Map<String, Object> reportData, int value, int completed, double avg) {
        assertTrue(reportData.containsKey("Count"));
        assertThat(reportData.get("Count"), is(value));
        assertTrue(reportData.containsKey("Completed"));
        assertThat(reportData.get("Completed"), is(completed));
        assertTrue(reportData.containsKey("Avg completion time"));
        assertThat(reportData.get("Avg completion time"), is(avg));
    }
}