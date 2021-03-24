package com.epolsoft.wtr.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epolsoft.wtr.entity.Factor;

public interface FactorRepository extends JpaRepository<Factor, Integer> {

	Factor findOneById(Integer id);
	
	List<Factor> findByName(String name);
    
    @Query(nativeQuery = true,value = "call get_list_of_factor(:factorName)")   // call store procedure with arguments
    List<Factor> getListOfFactors(@Param("factorName") String name);
    
    @Query("select f from Factor f where f.name = :name")
    List<Factor> getByName(@Param("name") String name);
}

