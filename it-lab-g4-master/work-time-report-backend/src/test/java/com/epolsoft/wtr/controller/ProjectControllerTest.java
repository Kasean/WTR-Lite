package com.epolsoft.wtr.controller;


import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.service.ProjectService;
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
public class ProjectControllerTest {

    @InjectMocks
    ProjectController projectController;

    @Mock
    ProjectService projectService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void AddProject()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Project project = new Project();
        project.setProjectID(1);

        when(projectService.createOrUpdate(any(Project.class))).thenReturn(project);

        Project projectToAdd = new Project("Project2");
        ResponseEntity<Object> responseEntity = projectController.addProject(projectToAdd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    public void FindAll()
    {
        Project project1 = new Project("Project1");
        Project project2 = new Project("Project2");

        List<Project> list = new ArrayList<Project>();
        list.addAll(Arrays.asList(project1, project2));

        when(projectService.findAll()).thenReturn(list);

        List<Project> result = projectController.getAllProjects();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getProjectID()).isEqualTo(project1.getProjectID());
        assertThat(result.get(1).getProjectID()).isEqualTo(project2.getProjectID());
    }
}
