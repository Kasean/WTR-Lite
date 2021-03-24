package com.epolsoft.wtr.controller;


import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Task;
import com.epolsoft.wtr.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/task")
public class TaskController {


    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTask()
    {
        return taskService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Task getByIdTask(@PathVariable int id) {
        return taskService.findById(id);

    }

    @PostMapping()
    public ResponseEntity<Object> addTask(@RequestBody Task task)
    {
        task = taskService.createOrUpdate(task);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/byFeature/{featureId}")
    public List<Task> getTasksByFeatureId(@PathVariable("featureId") int id) {
        return taskService.findTasksByFeatureId(id);
    }

}
