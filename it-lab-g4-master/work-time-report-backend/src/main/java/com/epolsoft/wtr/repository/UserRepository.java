package com.epolsoft.wtr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epolsoft.wtr.entity.User;

public interface UserRepository extends JpaRepository <User, Integer> {

    User findByUserId(Integer userId);
        
    @Query("select u from User u where u.userName = :userName")
    User findByName(@Param("userName") String name);

}
