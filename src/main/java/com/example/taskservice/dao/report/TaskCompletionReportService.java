package com.example.taskservice.dao.report;

import com.example.taskservice.model.Task;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskCompletionReportService extends AbstractReportService {
    {
        reportType = ReportType.TASK_COMPLETION;
    }

    @Override
    Map<String, Object> buildReportData(Instant startDate, Instant endDate, Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        List<Task> byPeriod = getTaskRepo().findByPeriod(startDate, endDate);
        result.put("Count", byPeriod.size());
        result.put("Completed", (int) byPeriod.stream().filter(t -> t.getStatus().isCompleted()).count());
        return result;
    }
}
