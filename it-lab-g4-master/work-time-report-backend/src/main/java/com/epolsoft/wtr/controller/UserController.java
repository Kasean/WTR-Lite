package com.epolsoft.wtr.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.service.UserService;

@RestController
@RequestMapping(path = "/user")

public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public List<User> getAllUser()
    {
        return userService.findAll();
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@RequestBody User user)
    {
        user = userService.createOrUpdate(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getUserId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable int id) {

        return userService.findById(id);
    }

}
