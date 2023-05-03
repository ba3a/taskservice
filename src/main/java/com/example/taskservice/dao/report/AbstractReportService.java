package com.example.taskservice.dao.report;

import com.example.taskservice.dao.TaskRepo;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
public abstract class AbstractReportService {
    @Setter
    private TaskRepo taskRepo;
    protected ReportType reportType;

    public Report buildReport(Instant startDate, Instant endDate, Map<String, Object> params) {
        return Report.builder().startDate(startDate).endDate(endDate).report(buildReportData(startDate, endDate, params)).build();
    }

    abstract Map<String, Object> buildReportData(Instant startDate, Instant endDate, Map<String, Object> params);
}
