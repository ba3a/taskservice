package com.example.taskservice.dao;

import com.example.taskservice.exception.EmployeeNotActiveException;
import com.example.taskservice.exception.EmployeeNotFoundException;
import com.example.taskservice.exception.EmployeeNotSpecifiedException;
import com.example.taskservice.model.Task;
import com.example.taskservice.support.LocalTestSupport;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.taskservice.domain.Status.DONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class TaskRepoTest extends LocalTestSupport {
    @Test
    void addTask() {
        Task test = taskRepo.save(Task.builder().title("Test").assigneeId(1L).build());

        Optional<Task> byId = taskRepo.findById(test.getId());
        assertTrue(byId.isPresent());
        assertThat(byId.get().getTitle(), is("Test"));
    }

    @Test
    void noEmployeeThrows() {
        assertThrows(EmployeeNotSpecifiedException.class, () -> taskRepo.save(Task.builder().build()));
    }

    @Test
    void employeeNotFoundThrows() {
        assertThrows(EmployeeNotFoundException.class, () -> taskRepo.save(Task.builder().assigneeId(55L).build()));
    }

    @Test
    void employeeNotActiveThrows() {
        assertThrows(EmployeeNotActiveException.class, () -> taskRepo.save(Task.builder().assigneeId(5L).build()));
    }

    @Test
    void updateTask() {
        Task test = taskRepo.save(Task.builder().title("Test").assigneeId(1L).build());

        test.setStatus(DONE);
        taskRepo.save(test);

        Optional<Task> byId = taskRepo.findById(test.getId());
        assertTrue(byId.isPresent());
        Task task = byId.get();
        assertThat(task.getTitle(), is("Test"));
        assertThat(task.getStatus(), is(DONE));
    }

    @Test
    void getByPeriod() {
        em.createNativeQuery("""
                INSERT INTO Task (id, created_at, modified_at, status)
                VALUES
                (1, {ts '2023-01-01 18:47:52.69'}, {ts '2023-01-05 18:47:52.69'}, 'TO_DO'),         --active in given period
                (2, {ts '2023-01-01 18:47:52.69'}, {ts '2023-01-05 18:47:52.69'}, 'DONE'),          --ended before given period
                (3, {ts '2023-05-01 18:47:52.69'}, {ts '2023-05-05 18:47:52.69'}, 'IN_PROGRESS'),   --started in given period
                (4, {ts '2023-05-01 18:47:52.69'}, {ts '2023-08-05 18:47:52.69'}, 'DONE'),          --started in given period, ended after
                (5, {ts '2023-01-05 18:47:52.69'}, {ts '2023-05-05 18:47:52.69'}, 'DONE'),          --ended in given period
                (6, {ts '2023-06-05 18:47:52.69'}, {ts '2023-08-06 18:47:52.69'}, 'DONE')           --started after given period
                """).executeUpdate();
        List<Task> byPeriod = taskRepo.findByPeriod(Instant.parse("2023-04-01T10:15:30.00Z"), Instant.parse("2023-06-01T10:15:30.00Z"));
        assertFalse(byPeriod.isEmpty());
        assertThat(byPeriod.size(), is(4));
        List<Long> ids = byPeriod.stream().map(Task::getId).toList();
        assertTrue(ids.containsAll(Arrays.asList(1L, 3L, 4L, 5L)));
    }

}