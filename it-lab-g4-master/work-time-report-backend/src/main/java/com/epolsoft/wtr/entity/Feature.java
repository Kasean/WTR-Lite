package com.epolsoft.wtr.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "feature")

public class Feature {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "featureId", nullable = false)
    private Integer featureId;

    @Column(name = "featureName", length = 999, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="projectId", nullable=false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
   // @JsonIgnore
    private Project project;
    
    @OneToMany(mappedBy="feature", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Task> tasks;
    
    @OneToMany(mappedBy="feature", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnore
    private Set<Report> reports;

    public Feature()
    {
    }
    
    public Feature(String name)
    {
    	this.name = name;
    }

    public Integer getFeatureId()
    {
        return featureId;
    }

    public void setFeatureId(Integer featureId)
    {
        this.featureId = featureId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public Set<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(Set<Task> tasks)
    {
        this.tasks = tasks;
    }

    public Set<Report> getReports()
    {
        return reports;
    }

    public void setReports(Set<Report> reports)
    {
        this.reports = reports;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Feature other = (Feature) obj;
        if (featureId == null)
        {
            if (other.featureId != null)
                return false;
        }
        else if (!featureId.equals(other.featureId))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Feature [featureId=" + featureId + ", name=" + name + "]";
    }
    
}
