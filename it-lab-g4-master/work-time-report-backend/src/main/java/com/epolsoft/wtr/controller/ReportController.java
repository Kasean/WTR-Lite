package com.epolsoft.wtr.controller;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epolsoft.wtr.entity.Report;
import com.epolsoft.wtr.excepcion.NotFoundException;
import com.epolsoft.wtr.repository.ReportRepository;
import com.epolsoft.wtr.service.ReportService;

@RestController
@RequestMapping(path = "/report")
public class ReportController {
	
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

	@Autowired
    private ReportService reportService;
	private ReportRepository reportRep;
	
	public List<Report> getAllReport()
    {
        return reportService.findAll();
    }
	
	@PostMapping()
    public ResponseEntity<Object> addReport(@RequestBody Report report)
    {
        report = reportService.createOrUpdate(report);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(report.getReportId()).toUri();
        return ResponseEntity.created(location).build();
    }
	
	@GetMapping(path = "/{id}")
    public Report getReport(@PathVariable int id) {
        return reportService.findById(id);
    }
	
	/*@GetMapping(path = "/byUserOrdered/{userId}")
    public List<Report> getReportByUserIdWithOrder(@PathVariable("userId") int id) {
        return reportRep.findByUserIdOrderByProject_ProjectId(id);
    }*/
	
	/*@GetMapping(path = "/fromDate/{date}")
    public List<Report> getByDateGreaterThan(@PathVariable("date") String date) {
        return reportRep.findByDateAfter(createDate(date));
    }*/
	
	// во всех функциях дата принимается как строка вида yyyy-mm-dd
	/*@GetMapping(path = "/searchWithId")
    public List<Report> getReportByUserAndDates(@RequestParam Integer userId, @RequestParam String dateS, @RequestParam String dateF) {
		return reportService.findByUserAndDates(userId,dateS,dateF);
    }*/
	
	// если не указывать dateF, то поиск будет с даты введенной в dateS,
	// если не указывать dateS, то поиск будет до даты введенной в dateF
	// Если введены оба, то поиск будет между датами
	/*@GetMapping(path = "/withDates")
    public List<Report> getReportByDateBetween(@RequestParam (required = false) String dateS, @RequestParam (required = false) String dateF) {
		if (dateS.isEmpty()&&dateF.isEmpty()) {
			throw new NotFoundException("Error, date(s) was not imputed!");
		} else if (dateS.isEmpty()) {
			//вывести все репорты до dateF
		} else if (dateF.isEmpty()) {
			//вывести все репорты по dateS
		}
        return reportService.findBetweenDate(dateS,dateF);
    }*/
	
	// если не указывать dateF, то поиск будет по дате введенной в date, если введен, то поиск будет между(!) датами date и dateF. Пример заполнения /report/byUser/1?date=2012-12-31&dateF=2050-12-12
	@GetMapping("/byUser/{userId}")
	public List<Report> getReportByUserAndDatesBetweenOrOne(@PathVariable Integer userId, @RequestParam (required = false) String date, @RequestParam (required = false) String dateF) {
		if (dateF==null) {
			if (date==null) {
				return reportService.findByUserId(userId);
			} else {
				return reportService.findByUserAndDate(userId, date);
			}
		} else {
			if (date==null) {
				throw new NotFoundException("Error, don't enought data. date = ?");
			} else {
				return reportService.findByUserAndDates(userId, date, dateF);
			}
		}
	}
	
	@GetMapping("/byUser/{userId}/filter")
	public List<Report> getReportByUserwithFilter(@PathVariable Integer userId, @RequestParam (required = false) Integer featureId, @RequestParam (required = false) Integer projectId, @RequestParam (required = false) Integer taskId, @RequestParam (required = false) Integer factorId, @RequestParam (required = false) String date, @RequestParam (required = false) String dateF) {
		if (dateF==null) {
			if (date==null) {
				return reportService.findByData(featureId, projectId, taskId, factorId, userId);
			} else {
				return reportService.findByDataAndDate(featureId, projectId, taskId, factorId, userId, date);
			}
		} else {
			if (date==null) {
				throw new NotFoundException("Error, don't enought data. date = ?");
			} else {
				return reportService.findByDataAndDates(featureId, projectId, taskId, factorId, userId, date, dateF);
			}
		}
	}
	
	@GetMapping()
	public List<Report> getAllReports() {
		return reportService.findAll();
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Report> updateReport(@PathVariable int id, @RequestBody Report report) {
		Report reportUpdated = reportService.createOrUpdate(report);
		return new ResponseEntity<Report>(report, HttpStatus.OK);
	}
}
