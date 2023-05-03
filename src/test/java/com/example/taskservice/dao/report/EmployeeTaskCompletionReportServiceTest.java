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

import static com.example.taskservice.dao.report.EmployeeTaskCompletionReportService.EMPLOYEE_ID_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class EmployeeTaskCompletionReportServiceTest extends LocalTestSupport {
    @AfterTestClass
    void reset() {
        Mockito.reset(taskRepo);
    }

    @Test
    void countTasks() {
        List<Task> tasks = buildTasks();
        doReturn(tasks).when(taskRepo).findByPeriod(any(), any());
        employeeTaskCompletionReportService.setTaskRepo(taskRepo);

        Map<String, Object> reportData = employeeTaskCompletionReportService.buildReportData(Instant.EPOCH, Instant.MAX, Map.of(EMPLOYEE_ID_PARAMETER, 1L));
        assertFalse(reportData.isEmpty());
        assertTrue(reportData.containsKey("Count"));
        assertThat(reportData.get("Count"), is(2));
        assertTrue(reportData.containsKey("Completed"));
        assertThat(reportData.get("Completed"), is(1));
    }

    List<Task> buildTasks() {
        return Arrays.asList(
                Task.builder().status(Status.TO_DO).assigneeId(1L).build(),
                Task.builder().status(Status.DONE).assigneeId(1L).build(),
                Task.builder().status(Status.DONE).assigneeId(2L).build()
        );
    }

}