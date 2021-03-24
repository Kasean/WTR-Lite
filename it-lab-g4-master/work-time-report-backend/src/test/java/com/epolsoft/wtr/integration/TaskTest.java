package com.epolsoft.wtr.integration;


import com.epolsoft.wtr.WtrApplication;
import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Task;
import com.epolsoft.wtr.repository.TaskRepository;
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
import static org.hamcrest.Matchers.*;
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

public class TaskTest extends BaseIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskTest.class);
    private static final Integer EXISTING_TASK_ID = 1;

    @Autowired
    private TaskRepository taskRepo;



    @Test
    @Sql("classpath:test_data.sql")
    public void createTask_shouldOK() throws Exception {
        long numberOfTasksBeforeTest = taskRepo.count();
        LOGGER.info("Number of tasks before test: " + numberOfTasksBeforeTest);

        Task taskToAdd = new Task();
        taskToAdd.setName("TaskToAdd#1");
        Feature feature = new Feature();
        feature.setFeatureId(1);
        taskToAdd.setFeature(feature);

        // @formatter:off
        ResultActions result = mockMvc.perform(post("/task")
                .contentType(CONTENT_TYPE_JSON)
                .content(objectMapper.writeValueAsString(taskToAdd)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/task/[0-9]+")));
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        long numberOfTaskAfterTest = taskRepo.count();
        LOGGER.info("Number of tasks after test: " + numberOfTaskAfterTest);
        assertThat(numberOfTasksBeforeTest, is(numberOfTaskAfterTest - 1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getTaskById_shouldOK() throws Exception {
        Task existingTask = taskRepo.findOneById(EXISTING_TASK_ID);
        assertThat(existingTask, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/task/{ID}", EXISTING_TASK_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        Task obtainedTask = objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Task.class);

        assertThat(obtainedTask, is(existingTask));
    }


    //@Disabled //TODO: enable when implemented
    @Test
    @Sql("classpath:test_data.sql")
    public void getTaskById_should404() throws Exception {
        Integer notExistingTaskId = 999;
        Task notExistingTask = taskRepo.findOneById(notExistingTaskId);
        assertThat(notExistingTask, nullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/task/{ID}", notExistingTaskId)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isNotFound());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getTasks_shouldOK() throws Exception {

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/task")
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Task> tasks = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Task[].class));

        assertThat(tasks.size(), greaterThanOrEqualTo(2));
    }


}
