package com.epolsoft.wtr.service;

import com.epolsoft.wtr.entity.Task;
import com.epolsoft.wtr.repository.TaskRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)

public class TaskServiceTest {

    private static Task p1;

    @Mock
    private TaskRepository taskCRUD;

    @InjectMocks
    private TaskService taskService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Task(1,"1");
    }


    @Test
    public void findById() {
        /*taskCRUD.deleteById(1);
        Mockito.verify(taskCRUD, Mockito.times(1)).deleteById(1);*/
        Mockito.when(taskCRUD.findOneById(1)).thenReturn(p1);
        assertThat(taskService.findById(1), is(p1));
        Mockito.verify(taskCRUD, Mockito.times(1)).findOneById(1);
    }
    
    @Test
    public void findByName() {
    	List<Task> p = new ArrayList<>();
    	p.add(p1);
        Mockito.when(taskCRUD.findByName("Task1")).thenReturn(p);
        assertThat(taskService.findByTaskName("Task1"), is(p));
        Mockito.verify(taskCRUD, Mockito.times(1)).findByName("Task1");
    }

}
