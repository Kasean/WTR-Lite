package com.epolsoft.wtr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epolsoft.wtr.entity.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    List<Feature> findAll();// поиск всех
    Feature findByFeatureId(Integer featureId);// поиск по Id

    @Query("select f from Feature f where f.name = :featureName")
    List<Feature> findByName(@Param("featureName") String name);

    //@Query("select r from Feature r where r.project = :projectId")
    List<Feature> findByProject_ProjectID(Integer projectID);
}
