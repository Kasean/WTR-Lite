package com.epolsoft.wtr.repository;

import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findOneById(Integer taskId);

    @Query("select t from Task t where t.name = :name")
    List<Task> findByName(@Param("name") String name);

    List<Task> findByFeature_FeatureId(Integer featureId);

}
