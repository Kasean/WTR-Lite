package com.epolsoft.wtr.controller;

import java.net.URI;
import java.util.ArrayList;
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

import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.service.ProjectService;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping(path = "/", produces = "application/json")
	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		for (Project project : projectService.findAll()) {
			projects.add(project);
		}
		return projects;
	}
	

	@GetMapping(path = "/{id}", produces = "application/json")
	public Project getProject(@PathVariable int id) {
		return projectService.findById(id);
	}
	
	@GetMapping(path = "/byuser/{userId}")
    public List<Project> getProjectByUserId(@PathVariable Integer userId) {
        return projectService.findByUserId(userId);
    }
	
	@PostMapping()
    public ResponseEntity<Object> addProject(@RequestBody Project project)
    {
        project = projectService.createOrUpdate(project);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(project.getProjectID()).toUri();
        return ResponseEntity.created(location).build();
    }
}
