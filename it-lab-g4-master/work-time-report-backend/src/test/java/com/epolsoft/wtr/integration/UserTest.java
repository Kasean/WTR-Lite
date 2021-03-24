package com.epolsoft.wtr.integration;


import com.epolsoft.wtr.WtrApplication;
import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.repository.UserRepository;
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
public class UserTest extends BaseIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTest.class);
    private static final Integer EXISTING_USER_ID = 1;

    @Autowired
    private UserRepository userRepo;




    @Test
    @Sql("classpath:test_data.sql")
    public void createUser_shouldOK() throws Exception {
        long numberOfUserBeforeTest = userRepo.count();
        LOGGER.info("Number of users before test: " + numberOfUserBeforeTest);

        User userToAdd = new User();
        userToAdd.setUserName("UserToAdd#1");
        userToAdd.setUserPassword("password");

        long numberOfUserbeforTest = userRepo.count();

        // @formatter:off
        ResultActions result = mockMvc.perform(post("/user")
                .contentType(CONTENT_TYPE_JSON)
                .content(objectMapper.writeValueAsString(userToAdd)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/user/[0-9]+")));
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
        long numberOfUserAfterTest = userRepo.count();

        LOGGER.info("Number of users after test: " + numberOfUserAfterTest);
        assertThat(numberOfUserbeforTest, is(numberOfUserAfterTest -1));
    }


    @Test
    @Sql("classpath:test_data.sql")
    public void getUserById_shouldOK() throws Exception {
        User existingUser = userRepo.findByUserId(EXISTING_USER_ID);
        assertThat(existingUser, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/user/{ID}", EXISTING_USER_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        User obtainedUser = objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), User.class);

        assertThat(obtainedUser, is(existingUser));
    }

    //@Disabled //TODO: enable when implemented
    @Test
    @Sql("classpath:test_data.sql")
    public void getUserById_should404() throws Exception {
        Integer notExistingUserId = 999;
        User notExistingUser = userRepo.findByUserId(notExistingUserId);
        assertThat(notExistingUser, nullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/user/{ID}", notExistingUserId)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isNotFound());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
    }
    @Test
    @Sql("classpath:test_data.sql")
    public void getUsers_shouldOK() throws Exception {

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/user")
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<User> users = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), User[].class));

        assertThat(users.size(), greaterThanOrEqualTo(2));
    }

}
