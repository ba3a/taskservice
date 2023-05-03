package com.example.taskservice.controller;

import com.example.taskservice.dao.report.ReportType;
import com.example.taskservice.support.LocalTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Map;

import static com.example.taskservice.dao.report.EmployeeTaskCompletionReportService.EMPLOYEE_ID_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportControllerTest extends LocalTestSupport {
    final static Instant START_DATE = Instant.ofEpochMilli(100L);
    final static Instant END_DATE = Instant.ofEpochMilli(200L);

    @Autowired
    ReportController reportController;

    @Captor
    ArgumentCaptor<Instant> startDateCaptor;
    @Captor
    ArgumentCaptor<Instant> endDateCaptor;

    @Captor
    ArgumentCaptor<Map<String, Object>> paramsCaptor;

    @Captor
    ArgumentCaptor<ReportType> reportTypeCaptor;

    @Test
    void getCompletionReport() {
        reportController.getCompletionReport(START_DATE, END_DATE);
        Mockito.verify(reportsBuilder).getReport(startDateCaptor.capture(), endDateCaptor.capture(), paramsCaptor.capture(), reportTypeCaptor.capture());

        assertThat(startDateCaptor.getValue(), is(START_DATE));
        assertThat(endDateCaptor.getValue(), is(END_DATE));
        assertTrue(paramsCaptor.getValue().isEmpty());
        assertThat(reportTypeCaptor.getValue(), is(ReportType.TASK_COMPLETION));
    }

    @Test
    void getEmployeeCompletionReport() {
        reportController.getEmployeeCompletionReport(START_DATE, END_DATE, 1L);
        Mockito.verify(reportsBuilder).getReport(startDateCaptor.capture(), endDateCaptor.capture(), paramsCaptor.capture(), reportTypeCaptor.capture());

        assertThat(startDateCaptor.getValue(), is(START_DATE));
        assertThat(endDateCaptor.getValue(), is(END_DATE));
        assertFalse(paramsCaptor.getValue().isEmpty());
        assertTrue(paramsCaptor.getValue().containsKey(EMPLOYEE_ID_PARAMETER));
        assertThat(paramsCaptor.getValue().get(EMPLOYEE_ID_PARAMETER), is(1L));
        assertThat(reportTypeCaptor.getValue(), is(ReportType.EMPLOYEE_TASK_COMPLETION));
    }

    @Test
    void getAvgTimeToCompletionReport() {
        reportController.getAvgTimeToCompletionReport(START_DATE, END_DATE);
        Mockito.verify(reportsBuilder).getReport(startDateCaptor.capture(), endDateCaptor.capture(), paramsCaptor.capture(), reportTypeCaptor.capture());

        assertThat(startDateCaptor.getValue(), is(START_DATE));
        assertThat(endDateCaptor.getValue(), is(END_DATE));
        assertTrue(paramsCaptor.getValue().isEmpty());
        assertThat(reportTypeCaptor.getValue(), is(ReportType.AVG_TIME_TO_COMPLETION));
    }
}