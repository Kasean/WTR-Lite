package com.epolsoft.wtr.controller;


import com.epolsoft.wtr.entity.Task;
import com.epolsoft.wtr.service.TaskService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @InjectMocks
    TaskController taskController;

    @Mock
    TaskService taskService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddEmployee()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Task task = new Task();
        task.setId(1);

        when(taskService.createOrUpdate(any(Task.class))).thenReturn(task);

        Task taskToAdd = new Task();
        ResponseEntity<Object> responseEntity = taskController.addTask(taskToAdd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");

    }

    @Test
    public void testFindAll() {
        Task task1 = new Task("1");
        Task task2 = new Task("2");

        List<Task> list = new ArrayList<Task>();
        list.addAll(Arrays.asList(task1, task2));

        when(taskService.findAll()).thenReturn(list);

        // when
        List<Task> result = taskController.getAllTask();

        // then
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getName())
                .isEqualTo(task1.getName());

        assertThat(result.get(1).getName())
                .isEqualTo(task2.getName());
    }



}
