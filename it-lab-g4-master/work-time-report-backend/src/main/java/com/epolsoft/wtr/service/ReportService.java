package com.epolsoft.wtr.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.excepcion.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epolsoft.wtr.entity.Report;
import com.epolsoft.wtr.repository.ReportRepository;

@Service
public class ReportService {
	
	@Autowired
    private ReportRepository reportRepository;
	
	private static Date createDate(String aDate)
    {
        Date newDate = new Date();

		SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd");
		myDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			newDate = myDate.parse(aDate);
		} catch (ParseException e) {

		}

		return newDate;
    }
	

    public Report findById(Integer id) {

        Report report = reportRepository.findOneByReportId(id);
        if (report == null) {
            throw new NotFoundException("Error report not");
        }
        return  report;
    }

    public List<Report> findAll() {
    	return reportRepository.findAll();
    }
    
    public Report createOrUpdate(Report report) {
        if (report.equals(null)) {
            throw new NotFoundException("Report was not received");
        }
        return reportRepository.save(report);
    }

    public List<Report> findByUserAndDates(Integer userId, String dateS, String dateF){
    	return  reportRepository.findByUserIdAndDateBetween(userId, createDate(dateS), createDate(dateF));
    }
    
    public List<Report> findByDataAndDates(Integer featureId, Integer projectId, Integer taskId, Integer factorId, Integer userId, String dateS, String dateF){
    	Set<Report> inter = new HashSet<Report>();
    	if (featureId!=null) {
    		Set<Report> features = new HashSet<Report>(reportRepository.findByFeatureIdAndUserIdAndDateBetween(featureId, userId, createDate(dateS), createDate(dateF)));
    		inter = features;
    	}
    	if (projectId!=null) {
    		Set<Report> projects = new HashSet<Report>(reportRepository.findByProjectIDAndUserIdAndDateBetween(projectId, userId, createDate(dateS), createDate(dateF)));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(projects);
    		} else {
    			inter = projects;
    		}
    	}
    	if (taskId!=null) {
    		Set<Report> tasks = new HashSet<Report>(reportRepository.findByIdAndUserIdAndDateBetween(taskId, userId, createDate(dateS), createDate(dateF)));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(tasks);
    		} else {
    			inter = tasks;
    		}
    	}
    	if (factorId!=null) {
    		Set<Report> factors = new HashSet<Report>(reportRepository.findByFactorIdAndUserIdAndDateBetween(factorId, userId, createDate(dateS), createDate(dateF)));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(factors);
    		} else {
    			inter = factors;
    		}
    	}
    	
    	if (inter!=null && !(inter.isEmpty())) {
    		List<Report> intersection = new ArrayList<Report>(inter);
			return  intersection;
		} else {
			throw new NotFoundException("Was not found reports");
		}
    }

    public List<Report> findByUserId(Integer id) {

        List<Report> report = reportRepository.findByUserId(id);
        if (report == null) {
            throw new NotFoundException("Error user not");
        }
        return  report;
    }
    
    public List<Report> findByData(Integer featureId, Integer projectId, Integer taskId, Integer factorId, Integer userId) {
    	Set<Report> inter = new HashSet<Report>();
    	if (featureId!=null) {
    		Set<Report> features = new HashSet<Report>(reportRepository.findByFeatureIdAndUserId(featureId, userId));
    		inter = features;
    	}
    	if (projectId!=null) {
    		Set<Report> projects = new HashSet<Report>(reportRepository.findByProjectIDAndUserId(projectId, userId));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(projects);
    		} else {
    			inter = projects;
    		}
    	}
    	if (taskId!=null) {
    		Set<Report> tasks = new HashSet<Report>(reportRepository.findByIdAndUserId(taskId, userId));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(tasks);
    		} else {
    			inter = tasks;
    		}
    	}
    	if (factorId!=null) {
    		Set<Report> factors = new HashSet<Report>(reportRepository.findByFactorIdAndUserId(factorId, userId));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(factors);
    		} else {
    			inter = factors;
    		}
    	}
    	
    	if (inter!=null && !(inter.isEmpty())) {
    		List<Report> intersection = new ArrayList<Report>(inter);
			return  intersection;
		} else {
			throw new NotFoundException("Was not found reports");
		}
    }
       
    /*public List<Report> findBetweenDate(String dateS, String dateF) {
        return  reportRepository.findByDateBetween(createDate(dateS), createDate(dateF));
    }*/
    
    public List<Report> findByUserAndDate(Integer userId, String sDate) {
    	return reportRepository.findByUserIdAndDate(userId, createDate(sDate));
    	/*List <Report> ch = reportRepository.findByUserId(user.getUserId());
        System.out.println(ch);
        for(Report i:ch) {
        	if (!(i.getDate().equals(date))) ch.remove(i);
        }
        return ch;*/
    }
    
    public List<Report> findByDataAndDate(Integer featureId, Integer projectId, Integer taskId, Integer factorId, Integer userId, String date) {
    	Set<Report> inter = new HashSet<Report>();
    	if (featureId!=null) {
    		Set<Report> features = new HashSet<Report>(reportRepository.findByUserIdAndFeatureIdAndDate(userId, featureId, createDate(date)));
    		inter = features;
    	}
    	if (projectId!=null) {
    		Set<Report> projects = new HashSet<Report>(reportRepository.findByUserIdAndProjectIDAndDate(userId, projectId, createDate(date)));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(projects);
    		} else {
    			inter = projects;
    		}
    	}
    	if (taskId!=null) {
    		Set<Report> tasks = new HashSet<Report>(reportRepository.findByUserIdAndIdAndDate(userId, taskId, createDate(date)));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(tasks);
    		} else {
    			inter = tasks;
    		}
    	}
    	if (factorId!=null) {
    		Set<Report> factors = new HashSet<Report>(reportRepository.findByUserFactorIdAndIdAndDate(userId, factorId, createDate(date)));
    		if (inter!=null && !(inter.isEmpty())) {
    			inter.retainAll(factors);
    		} else {
    			inter = factors;
    		}
    	}
    	
    	if (inter!=null && !(inter.isEmpty())) {
    		List<Report> intersection = new ArrayList<Report>(inter);
			return  intersection;
		} else {
			throw new NotFoundException("Was not found reports");
		}
    }
}