package com.epolsoft.wtr.integration;

import com.epolsoft.wtr.WtrApplication;
import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.repository.FeatureRepository;
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
public class FeatureTest extends BaseIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureTest.class);
    private static final Integer EXISTING_FEAETURE_ID = 1;

    @Autowired
    private FeatureRepository featureRepo;
    @Autowired
    private ProjectRepository projectRepo;

    @Test
    @Sql("classpath:test_data.sql")
    public void createFeature_shouldOK() throws Exception {
        long numberOfFeaturesBeforeTest = featureRepo.count();
        LOGGER.info("Number of features before test: " + numberOfFeaturesBeforeTest);

        Project testProject = new Project();
        testProject.setProjectID(1);

        Feature featureToAdd = new Feature();
        featureToAdd.setProject(testProject);
        featureToAdd.setName("FeatureToAdd#1");

        // @formatter:off
        ResultActions result = mockMvc.perform(post("/feature")
                .contentType(CONTENT_TYPE_JSON)
                .content(objectMapper.writeValueAsString(featureToAdd)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/feature/[0-9]+")));
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        long numberOfBookAfterTest = featureRepo.count();
        LOGGER.info("Number of books after test: " + numberOfBookAfterTest);
        assertThat(numberOfBookAfterTest, is(numberOfFeaturesBeforeTest + 1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getFeatureById_shouldOK() throws Exception {
        Feature existingFeature = featureRepo.findByFeatureId(EXISTING_FEAETURE_ID);
        assertThat(existingFeature, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/feature/{ID}", EXISTING_FEAETURE_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        Feature obtainedFeature = objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Feature.class);

        assertThat(obtainedFeature, is(existingFeature));
    }


    @Test
    @Sql("classpath:test_data.sql")
    public void getFeatureById_should404() throws Exception {
        Integer notExistingFeatureId = 999;
        Feature notExistingFeature = featureRepo.findByFeatureId(notExistingFeatureId);
        assertThat(notExistingFeature, nullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/feature/{ID}", notExistingFeatureId)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isNotFound());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getFeatures_shouldOK() throws Exception {

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/feature")
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Feature> features = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Feature[].class));

        assertThat(features.size(), greaterThanOrEqualTo(2));
    }
    @Test
    @Sql("classpath:test_data.sql")
    public void getFeaturesByProjectId_should_OK() throws Exception{
        Project p1 = new Project();
        p1.setProjectName("Project3");
        p1 = projectRepo.save(p1);
        Feature f1 = new Feature();
        f1.setName("Feature3");
        f1.setProject(p1);
        Feature f2 = new Feature();
        f2.setName("Feature3");
        f2.setProject(p1);
        f1 = featureRepo.save(f1);
        f2 = featureRepo.save(f2);
        // @formatter:off
        ResultActions result = mockMvc.perform(get("/feature/byProject/{projectID}", p1.getProjectID())
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on
        result.andDo(MockMvcResultHandlers.print());
        List<Feature> obtainedReport = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Feature[].class));
        assertThat(obtainedReport.size(), is(2));
    }
}

