package com.example.taskservice.dao.report;

import com.example.taskservice.model.Task;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeTaskCompletionReportService extends AbstractReportService {
    {
        reportType = ReportType.EMPLOYEE_TASK_COMPLETION;
    }

    public static final String EMPLOYEE_ID_PARAMETER = "id";

    @Override
    Map<String, Object> buildReportData(Instant startDate, Instant endDate, Map<String, Object> params) {
        if (!params.containsKey(EMPLOYEE_ID_PARAMETER)) {
            return Collections.emptyMap();
        }

        Long id = (Long) params.get(EMPLOYEE_ID_PARAMETER);
        Map<String, Object> result = new HashMap<>();
        List<Task> byPeriod = getTaskRepo().findByPeriod(startDate, endDate).stream().filter(t -> id.equals(t.getAssigneeId())).toList();
        result.put("Count", byPeriod.size());
        result.put("Completed", (int) byPeriod.stream().filter(t -> t.getStatus().isCompleted()).count());
        return result;
    }
}
