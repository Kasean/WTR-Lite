package com.epolsoft.wtr.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity 
@Table(name="Project")
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="projectId")
	private Integer projectID;
	
	@Column(name="projectName")
	private String projectName;

    @OneToMany(mappedBy="project", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Feature> features;
    
    @OneToMany(mappedBy="project", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Report> reports;
    
    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Set<User> users;

    public Project()
    {
    }
    
    public Project(String name) {
    	projectName = name;
    }

    public Integer getProjectID()
    {
        return projectID;
    }

    public void setProjectID(Integer projectID)
    {
        this.projectID = projectID;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public Set<Feature> getFeatures()
    {
        return features;
    }

    public void setFeatures(HashSet<Feature> features)
    {
        this.features = features;
    }

    public Set<Report> getReports()
    {
        return reports;
    }

    public void setReports(Set<Report> reports)
    {
        this.reports = reports;
    }

    public Set<User> getUsers()
    {
        return users;
    }

    public void setUsers(Set<User> users)
    {
        this.users = users;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projectID == null) ? 0 : projectID.hashCode());
        result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Project other = (Project) obj;
        if (projectID == null)
        {
            if (other.projectID != null)
                return false;
        }
        else if (!projectID.equals(other.projectID))
            return false;
        if (projectName == null)
        {
            if (other.projectName != null)
                return false;
        }
        else if (!projectName.equals(other.projectName))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Project [projectID=" + projectID + ", projectName=" + projectName + "]";
    }
      
}
