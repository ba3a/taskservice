package com.example.taskservice.dao;

import com.example.taskservice.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.Instant;
import java.util.List;

@RepositoryRestResource
public interface TaskRepo extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    /**
     * @param startDate start of the period
     * @param endDate   end of the period
     * @return List of {@link Task @Tasks}Tasks that last over the given period,
     * for example were created or updated in that period
     * or were created before the end of the given period and were not completed
     */
    @Query(value = """
            SELECT t FROM Task t WHERE
            (t.createdAt < :endDate AND t.modifiedAt > :startDate)
            OR
            (t.modifiedAt < :endDate AND  NOT t.status = DONE)
            """)
    List<Task> findByPeriod(Instant startDate, Instant endDate);
}
