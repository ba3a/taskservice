package com.example.taskservice.dao.report;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Map;

@Value
@Builder
public class Report {
    Instant startDate;
    Instant endDate;

    Map<String, Object> report;
}
