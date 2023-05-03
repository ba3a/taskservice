package com.example.taskservice.dao.report;

import com.example.taskservice.support.LocalTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Collections;

import static com.example.taskservice.dao.report.ReportType.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;

class ReportsBuilderTest extends LocalTestSupport {
    @Autowired
    ReportsBuilder reportsBuilder;

    @Test
    void avgTimeReportInvokes() {
        reportsBuilder.getReport(Instant.EPOCH, Instant.EPOCH, Collections.emptyMap(), AVG_TIME_TO_COMPLETION);
        verify(avgTimeToCompletionReportService).buildReportData(any(), any(), anyMap());
    }

    @Test
    void employeeTaskReportInvokes() {
        reportsBuilder.getReport(Instant.EPOCH, Instant.EPOCH, Collections.emptyMap(), EMPLOYEE_TASK_COMPLETION);
        verify(employeeTaskCompletionReportService).buildReportData(any(), any(), anyMap());
    }

    @Test
    void taskCompletionReportInvokes() {
        reportsBuilder.getReport(Instant.EPOCH, Instant.EPOCH, Collections.emptyMap(), TASK_COMPLETION);
        verify(taskCompletionReportService).buildReportData(any(), any(), anyMap());
    }

}