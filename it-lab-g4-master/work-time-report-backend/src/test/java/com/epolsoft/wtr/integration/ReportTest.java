package com.epolsoft.wtr.integration;

import com.epolsoft.wtr.WtrApplication;
import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.entity.Report;
import com.epolsoft.wtr.entity.Task;
import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.repository.ReportRepository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {WtrApplication.class})
// @ActiveProfiles("test") TODO: now 'wtrlite' DB is used instead of 'wtrlite_test' (in all tests)
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql", config = @SqlConfig(separator = "##"))
public class ReportTest extends BaseIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportTest.class);
    private static final Integer EXISTING_ID = 1;
    
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
    private ReportRepository reportRepo;

    @Test
    @Sql("classpath:test_data.sql")
    public void createReport_shouldOK() throws Exception {
        long numberOfReportsBeforeTest = reportRepo.count();
        LOGGER.info("Number of reports before test: " + numberOfReportsBeforeTest);

        Report reportToAdd = new Report();
        User user = new User();
        user.setUserId(2);
        reportToAdd.setUser(user);
        Project proj = new Project();
        proj.setProjectID(1);
        reportToAdd.setProject(proj);
        Feature feat = new Feature();
        feat.setFeatureId(1);
        reportToAdd.setFeature(feat);
        Task task = new Task();
        task.setId(1);
        reportToAdd.setTask(task);
        Factor factor = new Factor();
        factor.setId(1);
        reportToAdd.setFactor(factor);
        reportToAdd.setDate(createDate("2020-12-12"));
        reportToAdd.setHours(8);
        reportToAdd.setWorkUnits(8);
        reportToAdd.setComment("com2");
        reportToAdd.setStatus("mystatus2");

        // @formatter:off
        ResultActions result = mockMvc.perform(post("/report")
                .contentType(CONTENT_TYPE_JSON)
                .content(objectMapper.writeValueAsString(reportToAdd)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/report/[0-9]+")));
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        long numberOfReportAfterTest = reportRepo.count();
        LOGGER.info("Number of reports after test: " + numberOfReportAfterTest);
        assertThat(numberOfReportsBeforeTest, is(numberOfReportAfterTest - 1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getReportById_shouldOK() throws Exception {
        Report existingReport = reportRepo.findOneByReportId(EXISTING_ID);
        assertThat(existingReport, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/report/{ID}", EXISTING_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        Report obtainedReport = objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report.class);

        assertThat(obtainedReport, is(existingReport));
    }

    //@Disabled //TODO: enable when implemented
    @Test
    @Sql("classpath:test_data.sql")
    public void getReportById_should404() throws Exception {
        Integer notExistingReportId = 999;
        Report notExistingReport = reportRepo.findOneByReportId(notExistingReportId);
        assertThat(notExistingReport, nullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/report/{ID}", notExistingReportId)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isNotFound());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getReports_shouldOK() throws Exception {

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/report")
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> reports = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));

        assertThat(reports.size(), greaterThanOrEqualTo(1));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void getReportsByUserId_shouldOK() throws Exception {
    	List<Report> existingReports = reportRepo.findByUserId(EXISTING_ID);
        assertThat(existingReports, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/report/byUser/{USERID}", EXISTING_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));
        
        assertThat(obtainedReport, is(existingReports));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void getReportsByData_shouldOK() throws Exception {
    	List<Report> existingReports = reportRepo.findByUserId(EXISTING_ID);
        assertThat(existingReports, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/report/byUser/{USERID}/filter?taskId={taskId}&factorId={factorId}&featureId={featureId}", EXISTING_ID, 1, 1, 2)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));
        
        assertThat(obtainedReport, is(existingReports));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void getReportsByUserAndDate_shouldOK() throws Exception {
    	List<Report> existingReports = reportRepo.findByUserIdAndDate(EXISTING_ID, createDate("2012-12-31"));
        assertThat(existingReports, notNullValue());
        // @formatter:off88564
        ResultActions result = mockMvc.perform(get("/report?date={date}&userId={userId}", "2012-12-31", 1) //report?date=2012-12-31&userId=1) /report/{USER}/{DATE}"
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk()); // createDate("2012-12-31")
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));
        
        assertThat(obtainedReport, is(existingReports));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void getReportsByUserAndDataAndDate_shouldOK() throws Exception {
    	List<Report> existingReports = reportRepo.findByUserIdAndProjectIDAndDate(2, 2, createDate("2012-12-30"));
        assertThat(existingReports, notNullValue());
        // @formatter:off88564
        ResultActions result = mockMvc.perform(get("/report/byUser/{USERID}/filter?taskId={taskId}&projectId={projectId}&date={date}", 2, 1, 2, "2012-12-30") //report?date=2012-12-31&userId=1) /report/{USER}/{DATE}"
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk()); // createDate("2012-12-31")
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));
        
        assertThat(obtainedReport, is(existingReports));
    }*/
    
    @Test
    @Sql("classpath:test_data.sql")
    public void getReportsByUserAndDates_shouldOK() throws Exception {
    	List<Report> existingReports = reportRepo.findByUserIdAndDateBetween(EXISTING_ID, createDate("2000-12-31"),createDate("2020-12-31"));
        assertThat(existingReports, notNullValue());
        // @formatter:off88564
        ResultActions result = mockMvc.perform(get("/report?dateF={dateF}&dateS={dateS}&userId={userId}", "2000-12-31", "2020-12-12", 1) //report?date=2012-12-31&userId=1) /report/{USER}/{DATE}"
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk()); // createDate("2012-12-31")
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));
        
        assertThat(obtainedReport, is(existingReports));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void getReportsBetweenDate_shouldOK() throws Exception {
    	List<Report> existingReports = reportRepo.findByUserIdAndDate(EXISTING_ID, createDate("2012-12-31"));
        assertThat(existingReports, notNullValue());
        // @formatter:off
        ResultActions result = mockMvc.perform(get("/report?dateF={dateF}&dateS={dateS}", "2020-12-12","2000-12-12") //report?date=2012-12-31&userId=1) /report/{USER}/{DATE}"
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk()); // createDate("2012-12-31")
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));
        
        assertThat(obtainedReport, is(existingReports));
    }
    
    /*@Test
    @Sql("classpath:test_data.sql")
    public void getReportsComplexOnlyByUser_shouldOK() throws Exception {
    	List<Report> existingReports = reportRepo.getListOfReports(EXISTING_ID);
        assertThat(existingReports, notNullValue());
        // @formatter:off
        ResultActions result = mockMvc.perform(get("/report/search/by?userId={userId}", 1) //report?date=2012-12-31&userId=1) /report/{USER}/{DATE}"
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk()); // createDate("2012-12-31")
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Report> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Report[].class));
        
        assertThat(obtainedReport, is(existingReports));
    }*/
}
