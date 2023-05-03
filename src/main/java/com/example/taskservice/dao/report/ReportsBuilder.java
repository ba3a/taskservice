package com.example.taskservice.dao.report;

import com.example.taskservice.dao.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportsBuilder {
    private final TaskRepo taskRepo;
    @Autowired
    List<AbstractReportService> reportServices;
    private Map<ReportType, AbstractReportService> cachedServices;

    public Report getReport(Instant startDate, Instant endDate, Map<String, Object> params, ReportType reportType) {
        if (null == cachedServices) {
            cachedServices = new EnumMap<>(ReportType.class);
            reportServices.forEach(rs -> {
                rs.setTaskRepo(taskRepo);
                cachedServices.put(rs.getReportType(), rs);
            });
        }
        return cachedServices.get(reportType).buildReport(startDate, endDate, params);
    }
}
