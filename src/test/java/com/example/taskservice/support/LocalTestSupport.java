package com.example.taskservice.support;

import com.example.taskservice.TestApp;
import com.example.taskservice.dao.TaskRepo;
import com.example.taskservice.dao.report.AvgTimeToCompletionReportService;
import com.example.taskservice.dao.report.EmployeeTaskCompletionReportService;
import com.example.taskservice.dao.report.ReportsBuilder;
import com.example.taskservice.dao.report.TaskCompletionReportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApp.class)
@ActiveProfiles({"local"})
@Transactional
public abstract class LocalTestSupport {
    //I put all the beans I want to mock here for spring to start only one context for all contextual tests.
    //I'll have to reset mocks between tests, but it's quicker

    @PersistenceContext
    protected EntityManager em;
    @SpyBean
    protected ReportsBuilder reportsBuilder;
    @SpyBean
    protected AvgTimeToCompletionReportService avgTimeToCompletionReportService;

    @SpyBean
    protected EmployeeTaskCompletionReportService employeeTaskCompletionReportService;

    @SpyBean
    protected TaskCompletionReportService taskCompletionReportService;

    @SpyBean
    protected TaskRepo taskRepo;
}