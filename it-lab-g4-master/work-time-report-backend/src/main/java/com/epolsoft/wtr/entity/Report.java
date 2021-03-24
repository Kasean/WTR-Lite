package com.epolsoft.wtr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "Report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportId", nullable = false)
    private Integer reportId;
    
    @Column(name = "hours", nullable = false)
    private Integer hours;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;
    
    @Column(name = "workUnits", nullable = false)
    private Integer workUnits;
    
    @Column(name = "comment", length = 999, nullable = false)
    private String comment;
    
    @Column(name = "status", length = 999, nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonIgnore
    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JoinColumn(name="projectId", nullable=false)
    private Project project;
    
    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonIgnore
    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JoinColumn(name="featureId", nullable=false)
    private Feature feature;
    
    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JoinColumn(name="taskId", nullable=true)
    private Task task;
    
    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JoinColumn(name="factorId", nullable=false)
    private Factor factor;
    
    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    //@JsonIgnore
    @JoinColumn(name="userId", nullable=false)
    private User user;

    public Report()
    {
        
    }
    public Report(Integer id) {
    	reportId = id;
    }

    public Integer getReportId()
    {
        return reportId;
    }

    public void setReportId(Integer reportId)
    {
        this.reportId = reportId;
    }

    public Integer getHours()
    {
        return hours;
    }

    public void setHours(Integer hours)
    {
        this.hours = hours;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getWorkUnits()
    {
        return workUnits;
    }

    public void setWorkUnits(Integer workUnits)
    {
        this.workUnits = workUnits;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public Feature getFeature()
    {
        return feature;
    }

    public void setFeature(Feature feature)
    {
        this.feature = feature;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }

    public Factor getFactor()
    {
        return factor;
    }

    public void setFactor(Factor factor)
    {
        this.factor = factor;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reportId == null) ? 0 : reportId.hashCode());
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
        Report other = (Report) obj;
        if (reportId == null)
        {
            if (other.reportId != null)
                return false;
        }
        else if (!reportId.equals(other.reportId))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Report [reportId=" + reportId + ", hours=" + hours + ", date=" + date + ", workUnits=" + workUnits
                + ", comment=" + comment + ", status=" + status + ", project=" + project + ", feature=" + feature
                + ", task=" + task + ", factor=" + factor + ", user=" + user + "]";
    }
    
}
