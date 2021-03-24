package com.epolsoft.wtr.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
//import java.sql.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;

import com.epolsoft.wtr.entity.Report;
import com.epolsoft.wtr.entity.Task;
import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Project;


//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql",
   config = @SqlConfig(separator = "##") )

public class ReportRepositoryTest {

	private static Report p1;
    private static Report p2;
    private static Report p1_modified;

    @Autowired
    private ReportRepository repository;

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
    
    @BeforeAll
    public static void init() {
        p1 = new Report();
        p1.setReportId(1);
        p1.setHours(8);
        User user = new User();
        user.setUserId(1);
        user.setUserName("User1");
        user.setUserPassword("pass1");
        Feature feach = new Feature();
        feach.setFeatureId(2);
        feach.setName("Feature2");       
        Project proj = new Project();
        proj.setProjectID(2);
        proj.setProjectName("Project2");
        Factor fact = new Factor();
        fact.setId(1);
        fact.setName("Factor1");
        p1.setFactor(fact);
        p1.setTask(null);
        p1.setFeature(feach);
        p1.setProject(proj);
        p1.setDate(createDate("2012-12-31"));
        p1.setUser(user);
        p1.setWorkUnits(8);
        p1.setComment("com1");
        p1.setStatus("mystatus1");
        p2 = new Report();
        p1_modified = new Report();
        //p1 = new Report(1, 1);
        //p2 = new Report(2, 1);
        //p1_modified = new Report(3, 1);
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findByReportId() {
    	/*List<Report> p = new ArrayList<>();
    	p.add(p1);*/
        assertThat(repository.findOneByReportId(1), is(p1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findByUser() {
        List<Report> report = repository.findByUserId(1);
        printReportStatus(report);
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndFeature() {
        List<Report> report = repository.findByFeatureIdAndUserId(2,1);
        printReportStatus(report);
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getFeature(), is(p1.getFeature()));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndProject() {
        List<Report> report = repository.findByProjectIDAndUserId(2,1);
        printReportStatus(report);
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getProject(), is(p1.getProject()));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndTask() { //крашится
        List<Report> report = repository.findByIdAndUserId(1,2);
        printReportStatus(report);
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getTask(), is(p1.getTask()));
    }*/
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndFactor() {
        List<Report> report = repository.findByFactorIdAndUserId(1,1);
        printReportStatus(report);
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getFactor(), is(p1.getFactor()));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void findByUserOrdered() {
        List<Report> report = repository.findByUserId(2);
        printReportStatus(report);
        assertThat(report.size(), is(2));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
    }*/
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndDate() {

        List<Report> report = repository.findByUserIdAndDate(p1.getUser().getUserId(), p1.getDate()); // попытка провернуть через java.sql.Date
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndFeatureAndDate() {

        List<Report> report = repository.findByUserIdAndFeatureIdAndDate(p1.getUser().getUserId(), p1.getFeature().getFeatureId(), p1.getDate());
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getFeature(), is(p1.getFeature()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndProjectAndDate() {

        List<Report> report = repository.findByUserIdAndProjectIDAndDate(p1.getUser().getUserId(), p1.getProject().getProjectID(), p1.getDate());
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getProject(), is(p1.getProject()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndTaskAndDate() { //крашится

        List<Report> report = repository.findByUserIdAndIdAndDate(p1.getUser().getUserId(), p1.getTask().getId(), p1.getDate());
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getTask(), is(p1.getTask()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }*/
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndFactorAndDate() {

        List<Report> report = repository.findByUserFactorIdAndIdAndDate(p1.getUser().getUserId(), p1.getFactor().getId(), p1.getDate());
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getFactor(), is(p1.getFactor()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserAndDates() {

        List<Report> report = repository.findByUserIdAndDateBetween(p1.getUser().getUserId(), createDate("2000-12-12"), createDate("2020-12-12"));
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getUser(), is(p1.getUser()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByFeatureAndDates() {

        List<Report> report = repository.findByFeatureIdAndUserIdAndDateBetween(p1.getFeature().getFeatureId(), p1.getUser().getUserId(), createDate("2000-12-12"), createDate("2020-12-12"));
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getFeature(), is(p1.getFeature()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByProjectAndDates() {

        List<Report> report = repository.findByProjectIDAndUserIdAndDateBetween(p1.getProject().getProjectID(), p1.getUser().getUserId(), createDate("2000-12-12"), createDate("2020-12-12"));
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getProject(), is(p1.getProject()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void findByTaskAndDates() { //крашится

        List<Report> report = repository.findByIdAndUserIdAndDateBetween(p1.getTask().getId(), p1.getUser().getUserId(), createDate("2000-12-12"), createDate("2020-12-12"));
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getTask(), is(p1.getTask()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }*/
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByFactorAndDates() {

        List<Report> report = repository.findByFactorIdAndUserIdAndDateBetween(p1.getFactor().getId(), p1.getUser().getUserId(), createDate("2000-12-12"), createDate("2020-12-12"));
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getFactor(), is(p1.getFactor()));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void findByDateBetween() {

        List<Report> report = repository.findByDateBetween(createDate("2010-12-31"),createDate("2014-12-31"));
        printReportStatus(report);
        
        assertThat(report.size(), is(1));
        assertThat(report.get(0).getDate(), is(p1.getDate()));
    }*/
 
    @Test
    @Sql("classpath:test_data.sql")
    public void findAll() {
        List<Report> iterableReports = repository.findAll();
        long numberOfReports = StreamSupport.stream(iterableReports.spliterator(), false).count();
        assertThat(numberOfReports, is(1L));
    }

    /*@Test
    public void save() {
        assertThat(repository.save(p1), is(p1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void update() {
        Optional<Report> report = repository.findById(1);
        if (report.isPresent()) {
            report.get().setReportId(p1_modified.getReportId());
            assertThat(repository.save(report.get()), is(p1_modified));
        } else {
            fail("Expected report '" + p1.getReportId() + "' is not found");
        }
    }

    /*@Test
    @Sql("classpath:test_data.sql")
    public void count() {
        assertThat(repository.count(), is(2L));
    }*/
    
    public void printReportStatus(List<Report> result)
    {
        for(Report s: result)
        {
        	System.out.println(s.toString());
        }   	
    }
    
}
