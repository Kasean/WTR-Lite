package com.epolsoft.wtr.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;

import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.entity.Report;
import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.repository.ReportRepository;

//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
@SqlMergeMode(MergeMode.MERGE)
@Sql(scripts = "classpath:test_data.sql",
   config = @SqlConfig(separator = "##") )
public class ReportServiceTest {

	private static Report p1, p2;

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
    
    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Report();
        p1.setReportId(1);
        User user = new User();
        user.setUserId(1);
        user.setUserName("User1");
        Project pro = new Project();
        pro.setProjectID(2);
        pro.setProjectName("Project2");
        Factor fact = new Factor();
        fact.setId(1);
        fact.setName("Factor1");
        p1.setUser(user);
        p1.setFactor(fact);
        p1.setProject(pro);
        p1.setDate(createDate("2012-12-31"));
        p2 = new Report(2);
        User user2 = new User();
        user2.setUserId(2);
        user2.setUserName("User2");
        p2.setUser(user2);
        p2.setDate(createDate("2012-12-31"));
       // p1 = new Report(1);
    }

    @Test
    public void findByUserId() {
    	List<Report> p = new ArrayList<>();
    	p.add(p1);
    	Mockito.when(reportRepository.findByUserId(1)).thenReturn(p);
        assertThat(reportService.findByUserId(1), is(p));
        Mockito.verify(reportRepository, Mockito.times(1)).findByUserId(1);
    }
        
    @Test
    public void findByReportId() {
    	/*List<Report> p = new ArrayList<>();
    	p.add(p1);*/
    	Mockito.when(reportRepository.findOneByReportId(1)).thenReturn(p1);
        assertThat(reportService.findById(1), is(p1));
        Mockito.verify(reportRepository, Mockito.times(1)).findOneByReportId(1);
    }
    
    @Test
    public void findByUserIdAndDate() {
    	List<Report> p = new ArrayList<>();
    	p.add(p1);
    	Mockito.when(reportRepository.findByUserIdAndDate(1, p1.getDate())).thenReturn(p);
        assertThat(reportService.findByUserAndDate(1, "2012-12-31"), is(p));
        Mockito.verify(reportRepository, Mockito.times(1)).findByUserIdAndDate(1, p1.getDate());
    }
    
    /*@Test
    public void findByDateBetween() {
    	List<Report> p = new ArrayList<>();
    	p.add(p1);
    	Mockito.when(reportRepository.findByDateBetween(createDate("2010-12-12"), createDate("2015-12-12"))).thenReturn(p);
        assertThat(reportService.findBetweenDate("2010-12-12", "2015-12-12"), is(p));
        Mockito.verify(reportRepository, Mockito.times(1)).findByDateBetween(createDate("2010-12-12"), createDate("2015-12-12"));
    }*/
    
    @Test
    public void findByUserIdAndDates() {
    	List<Report> p = new ArrayList<>();
    	p.add(p1);
    	Mockito.when(reportRepository.findByUserIdAndDateBetween(1,createDate("2010-12-12"), createDate("2015-12-12"))).thenReturn(p);
        assertThat(reportService.findByUserAndDates(1, "2010-12-12", "2015-12-12"), is(p));
        Mockito.verify(reportRepository, Mockito.times(1)).findByUserIdAndDateBetween(1,createDate("2010-12-12"), createDate("2015-12-12"));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void findByUserIdAndDate() {
    	List<Report> p = new ArrayList<>();
    	p.add(p1);
    	Mockito.when(reportRepository.findByUserAndDate(p1.getUser().getUserId(),p1.getDate())).thenReturn(p);
    	assertThat(reportService.findByUserAndDate(p1.getUser(),p1.getDate()), is(p));
    	Mockito.verify(reportRepository, Mockito.times(1)).findByUserAndDate(p1.getUser().getUserId(),p1.getDate());
    }*/
}
