package com.epolsoft.wtr.service;


import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Task;
import com.epolsoft.wtr.excepcion.NotFoundException;
import com.epolsoft.wtr.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks == null) {
            throw new NotFoundException("Error tasks not exist");
        }

        return tasks;
    }

    public List<Task> findByTaskName(String taskName) {
        List<Task> tasks = taskRepository.findByName(taskName);
        if (tasks== null){
            throw new NotFoundException("Error name was not imputed");
        }
        return tasks;
    }

    public Task createOrUpdate(Task task) {
        if ("".equals(task.getName())) {
            throw new ValidationException("User cannot have empty name");
        }
        return taskRepository.save(task);
    }

    public Task findById(Integer featureId) {
        Task task = taskRepository.findOneById(featureId);

        if (task == null) {
            throw new NotFoundException("Error user not");
        }
        return  task;

    }

    public List<Task> findTasksByFeatureId(Integer id) {

        List<Task> tasks = taskRepository.findByFeature_FeatureId(id);
        if (tasks == null) {
            throw new NotFoundException("Error tasks not exist");
        }
        return  tasks;
    }


}
