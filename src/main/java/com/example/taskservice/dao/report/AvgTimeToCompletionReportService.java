package com.example.taskservice.dao.report;

import com.example.taskservice.model.Task;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AvgTimeToCompletionReportService extends AbstractReportService {
    {
        reportType = ReportType.AVG_TIME_TO_COMPLETION;
    }

    @Override
    Map<String, Object> buildReportData(Instant startDate, Instant endDate, Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        List<Task> byPeriod = getTaskRepo().findByPeriod(startDate, endDate);
        result.put("Count", byPeriod.size());
        List<Task> completedTasks = byPeriod.stream().filter(t -> t.getStatus().isCompleted()).toList();
        result.put("Completed", completedTasks.size());
        result.put("Avg completion time", completedTasks.stream()
                .map(t -> Duration.between(t.getCreatedAt(), t.getModifiedAt())).mapToDouble(Duration::toDays)
                .average().orElse(Double.NaN));
        return result;
    }
}
