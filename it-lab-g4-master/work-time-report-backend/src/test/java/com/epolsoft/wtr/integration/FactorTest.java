package com.epolsoft.wtr.integration;

import com.epolsoft.wtr.WtrApplication;
import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.repository.FactorRepository;
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
public class FactorTest extends BaseIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FactorTest.class);
    private static final Integer EXISTING_FACTOR_ID = 1;

    @Autowired
    private FactorRepository factorRepo;

    @Test
    @Sql("classpath:test_data.sql")
    public void createFactor_shouldOK() throws Exception {
        long numberOfFactorsBeforeTest = factorRepo.count();
        LOGGER.info("Number of factors before test: " + numberOfFactorsBeforeTest);

        Factor factorToAdd = new Factor();
        factorToAdd.setName("FactorToAdd#1");

        // @formatter:off
        ResultActions result = mockMvc.perform(post("/factor")
                .contentType(CONTENT_TYPE_JSON)
                .content(objectMapper.writeValueAsString(factorToAdd)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/factor/[0-9]+")));
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        long numberOfFactorAfterTest = factorRepo.count();
        LOGGER.info("Number of factors after test: " + numberOfFactorAfterTest);
        assertThat(numberOfFactorAfterTest, is(numberOfFactorsBeforeTest + 1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getFactorById_shouldOK() throws Exception {
        Factor existingFactor = factorRepo.findOneById(EXISTING_FACTOR_ID);
        assertThat(existingFactor, notNullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/factor/{ID}", EXISTING_FACTOR_ID)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        Factor obtainedFactor = objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Factor.class);

        assertThat(obtainedFactor, is(existingFactor));
    }

    //@Disabled //TODO: enable when implemented
    @Test
    @Sql("classpath:test_data.sql")
    public void getFactorById_should404() throws Exception {
        Integer notExistingFactorId = 999;
        Factor notExistingFactor = factorRepo.findOneById(notExistingFactorId);
        assertThat(notExistingFactor, nullValue());

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/factor/{ID}", notExistingFactorId)
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isNotFound());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void getFactors_shouldOK() throws Exception {

        // @formatter:off
        ResultActions result = mockMvc.perform(get("/factor")
                .contentType(CONTENT_TYPE_JSON))
                .andExpect(status().isOk());
        // @formatter:on

        result.andDo(MockMvcResultHandlers.print());

        List<Factor> factors = Arrays.asList(objectMapper.readValue(result.andReturn()
                .getResponse()
                .getContentAsByteArray(), Factor[].class));

        assertThat(factors.size(), greaterThanOrEqualTo(2));
    }
}
