package com.example.taskservice.controller;

import com.example.taskservice.dao.report.Report;
import com.example.taskservice.dao.report.ReportsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

import static com.example.taskservice.dao.report.EmployeeTaskCompletionReportService.EMPLOYEE_ID_PARAMETER;
import static com.example.taskservice.dao.report.ReportType.*;
import static java.util.Collections.emptyMap;
import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportsBuilder reportsBuilder;

    @GetMapping("/completion")
    public Report getCompletionReport(@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) Instant startDate, @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) Instant endDate) {
        return reportsBuilder.getReport(startDate, endDate, emptyMap(), TASK_COMPLETION);
    }

    @GetMapping("/employee_completion")
    public Report getEmployeeCompletionReport(@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) Instant startDate, @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) Instant endDate, @RequestParam Long id) {
        return reportsBuilder.getReport(startDate, endDate, Map.of(EMPLOYEE_ID_PARAMETER, id), EMPLOYEE_TASK_COMPLETION);
    }

    @GetMapping("/avg_time_completion")
    public Report getAvgTimeToCompletionReport(@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) Instant startDate, @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) Instant endDate) {
        return reportsBuilder.getReport(startDate, endDate, emptyMap(), AVG_TIME_TO_COMPLETION);
    }
}
