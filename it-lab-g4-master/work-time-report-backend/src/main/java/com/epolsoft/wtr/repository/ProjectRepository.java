package com.epolsoft.wtr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.entity.Report;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
    Project findOneByProjectID(Integer projectID);
       
    @Query("select p from Project p where p.projectName = :projectName")
    List<Project> findByName(@Param("projectName") String name);

    List<Project> findByUsers_UserId(Integer userId);
}
