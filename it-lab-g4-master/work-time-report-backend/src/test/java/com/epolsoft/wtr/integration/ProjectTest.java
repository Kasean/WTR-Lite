package com.epolsoft.wtr.integration;

import com.epolsoft.wtr.WtrApplication;
import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.repository.ProjectRepository;
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

import java.util.Arrays;
import java.util.List;

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
public class ProjectTest extends BaseIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectTest.class);
    private static final Integer EXISTING_PROJECT_ID = 1;

    @Autowired
    private ProjectRepository projectRepo;

    @Test
    @Sql("classpath:test_data.sql")
    public void createProject_shouldOK() throws Exception {
        long numberOfProjectsBeforeTest = projectRepo.count();
        LOGGER.info("Number of projects before test: " + numberOfProjectsBeforeTest);

        Project projectToAdd = new Project();
        projectToAdd.setProjectName("ProjectToAdd#1");

        // @formatter:off
        ResultActions result = mockMvc.perform(post("/project")
                .contentType(CONTENT_TYPE_JSON)
                .content(objectMapper.writeValueAsString(projectToAdd)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/project/[0-9]+")));
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        long numberOfProjectAfterTest = projectRepo.count();
        LOGGER.info("Number of projects after test: " + numberOfProjectAfterTest);
        assertThat(numberOfProjectAfterTest, is(numberOfProjectsBeforeTest + 1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getProjectById_shouldOK() throws Exception {
        Project existingProject = projectRepo.findOneByProjectID(EXISTING_PROJECT_ID);
        assertThat(existingProject, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/project/{id}", EXISTING_PROJECT_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        Project obtainedProject = objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Project.class);

        assertThat(obtainedProject, is(existingProject));
    }

    //@Disabled //TODO: enable when implemented
    @Test
    @Sql("classpath:test_data.sql")
    public void getProjectById_should404() throws Exception {
        Integer notExistingProjectId = 999;
        Project notExistingProject = projectRepo.findOneByProjectID(notExistingProjectId);
        assertThat(notExistingProject, nullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/project/{id}", notExistingProjectId)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isNotFound());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getProjects_shouldOK() throws Exception {

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/project/")
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Project> projects = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Project[].class));

        assertThat(projects.size(), greaterThanOrEqualTo(2));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void getProjectByUserId_shouldOK() throws Exception {
         List<Project> existingProjects = projectRepo.findByUsers_UserId(EXISTING_PROJECT_ID);
        assertThat(existingProjects, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/project/byuser/{userId}", EXISTING_PROJECT_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Project> obtainedProject = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Project[].class));
        
        assertThat(obtainedProject, is(existingProjects));
        
    }
}
