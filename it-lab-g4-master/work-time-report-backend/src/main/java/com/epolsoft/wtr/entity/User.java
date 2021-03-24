package com.epolsoft.wtr.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", length = 10, nullable = false)
    private Integer userId;

    @Column(name = "userName", unique = true, length = 999, nullable = false)
    private String userName;
    
    @Column(name = "userPassword", unique = true, length = 999, nullable = false)
    private String userPassword;

    public User(String userName, String userPass) {
        this.userName = userName;
        userPassword = userPass;
    }

    public User(Integer userId, String userName, String userPass) {
        this.userId = userId;
        this.userName = userName;
        userPassword = userPass;
    }

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Report> reports;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JoinTable(
      name = "projects", 
      joinColumns = @JoinColumn(name = "userId"), 
      inverseJoinColumns = @JoinColumn(name = "projectId"))
    Set<Project> projects;



    public User()
    {
    }

    public Integer getUserId()
    {
        return userId;
    }
    
    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPass)
    {
        userPassword = userPass;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Set<Report> getReports()
    {
        return reports;
    }

    public void setReports(Set<Report> reports)
    {
        this.reports = reports;
    }

    public Set<Project> getLikedProjects()
    {
        return projects;
    }

    public void setLikedProjects(Set<Project> likedProjects)
    {
        this.projects = likedProjects;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((userPassword == null) ? 0 : userPassword.hashCode());
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
        User other = (User) obj;
        if (userId == null)
        {
            if (other.userId != null)
                return false;
        }
        else if (!userId.equals(other.userId))
            return false;
        if (userName == null)
        {
            if (other.userName != null)
                return false;
        }
        else if (!userName.equals(other.userName))
            return false;
        if (userPassword == null)
        {
            if (other.userPassword != null)
                return false;
        }
        else if (!userPassword.equals(other.userPassword))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + "]";
    }

    
}