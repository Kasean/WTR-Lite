package com.epolsoft.wtr.service;

import java.util.List;

import javax.validation.ValidationException;

import com.epolsoft.wtr.excepcion.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project findById(Integer id) {
		Project project = projectRepository.findOneByProjectID(id);
		if (project == null) {
			throw new NotFoundException("Error project was not found");
		}
		return  project;
	}

	public Iterable<Project> findAll() {
		Iterable<Project> projects = projectRepository.findAll();
		if (projects == null){
			throw new NotFoundException("Error project was not imputed");
		}
		return projects;
	}

	public List <Project> findByName(String projectName){
		List<Project> projects = projectRepository.findByName(projectName);
		if (projects == null) {
			throw new NotFoundException("Error name was not imputed");
		}
		return projects;
	}
	
	public List <Project> findByUserId(Integer userId){
		List<Project> projects = projectRepository.findByUsers_UserId(userId);
		if (projects == null) {
			throw new NotFoundException("Error userId was not imputed");
		}
		return projects;
	}

	public Project createOrUpdate(Project project) {
		if ("".equals(project.getProjectName())) {
            throw new ValidationException("Project cannot have empty name");
        }
        return projectRepository.save(project);
	}

}
