package com.example.taskservice.dao.report;

import com.example.taskservice.support.LocalTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.time.Instant;

import static java.time.Duration.ofDays;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class AbstractReportServiceTest extends LocalTestSupport {
    @AfterTestClass
    void reset() {
        Mockito.reset(taskRepo);
    }

    @Test
    void buildReport() {
        doReturn(emptyList()).when(taskRepo).findByPeriod(any(), any());
        taskCompletionReportService.setTaskRepo(taskRepo);
        
        Report report = taskCompletionReportService.buildReport(Instant.EPOCH, Instant.EPOCH.plus(ofDays(1)), emptyMap());
        assertThat(report.getStartDate(), is(Instant.EPOCH));
        assertThat(report.getEndDate(), is(Instant.EPOCH.plus(ofDays(1))));
    }
}