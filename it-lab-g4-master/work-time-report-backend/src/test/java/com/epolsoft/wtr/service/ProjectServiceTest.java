package com.epolsoft.wtr.service;

import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.repository.ProjectRepository;
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

import javax.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    private static Project p1;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Project("Project1");
        p1.setProjectID(1);
    }

    @Test
    public void findById() {
    	Mockito.when(projectRepository.findOneByProjectID(1)).thenReturn(p1);
        assertThat(projectService.findById(1), is(p1));
        Mockito.verify(projectRepository, Mockito.times(1)).findOneByProjectID(1);
    }
    
    @Test
    public void findByName() {
    	List<Project> p = new ArrayList<>();
    	p.add(p1);
        Mockito.when(projectRepository.findByName("Project1")).thenReturn(p);
        assertThat(projectService.findByName("Project1"), is(p));
        Mockito.verify(projectRepository, Mockito.times(1)).findByName("Project1");
    }
    
    @Test
    public void findByUserId() {
    	List<Project> p = new ArrayList<>();
    	p.add(p1);
        Mockito.when(projectRepository.findByUsers_UserId(1)).thenReturn(p);
        assertThat(projectService.findByUserId(1), is(p));
        Mockito.verify(projectRepository, Mockito.times(1)).findByUsers_UserId(1);
    }
}
