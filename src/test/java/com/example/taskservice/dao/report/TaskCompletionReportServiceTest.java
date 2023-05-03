package com.example.taskservice.dao.report;

import com.example.taskservice.domain.Status;
import com.example.taskservice.model.Task;
import com.example.taskservice.support.LocalTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class TaskCompletionReportServiceTest extends LocalTestSupport {
    @AfterTestClass
    void reset() {
        Mockito.reset(taskRepo);
    }

    @Test
    void countTasks() {
        doReturn(buildTasks()).when(taskRepo).findByPeriod(any(), any());
        taskCompletionReportService.setTaskRepo(taskRepo);

        Map<String, Object> reportData = taskCompletionReportService.buildReportData(Instant.EPOCH, Instant.MAX, emptyMap());
        assertFalse(reportData.isEmpty());
        assertTrue(reportData.containsKey("Count"));
        assertThat(reportData.get("Count"), is(3));
        assertTrue(reportData.containsKey("Completed"));
        assertThat(reportData.get("Completed"), is(2));
    }

    List<Task> buildTasks() {
        return Arrays.asList(
                Task.builder().status(Status.TO_DO).build(),
                Task.builder().status(Status.DONE).build(),
                Task.builder().status(Status.DONE).build()
        );
    }

}