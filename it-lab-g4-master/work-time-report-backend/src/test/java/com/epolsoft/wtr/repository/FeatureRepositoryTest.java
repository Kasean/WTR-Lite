package com.epolsoft.wtr.repository;

import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Project;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;

import javax.annotation.processing.FilerException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql",
        config = @SqlConfig(separator = "##") )


public class FeatureRepositoryTest {


    private static Feature p1;
    private static Feature p2;
    private static Feature p1_modified;
    private  static Project p3;

    @Autowired
    private FeatureRepository repository;

    @BeforeAll
    public static void init() {
        p1 = new Feature();
        p1.setFeatureId(1);
        p1.setName("Feature1");

        p2 = new Feature();
        p1_modified = new Feature();
        p3 = new Project();
        p3.setProjectID(1);
    }


    @Test
    @Sql("classpath:test_data.sql")
    public void findById() {
        assertThat(repository.findById(1), is(Optional.of(p1)));
    }


    @Test
    @Sql("classpath:test_data.sql")
    public void findAll() {
        List<Feature> iterableFeature = repository.findAll();
        long numberOfFeature = iterableFeature.size();
        assertThat(numberOfFeature, is(2L));
    }


    @Test
    @Sql("classpath:test_data.sql")
    public void findByName(){
        List<Feature> features = repository.findByName("Feature1");
        assertThat(features.size(), is(1));
        assertThat(features.get(0).getName(), is("Feature1"));

    }


    @Test
    @Sql("classpath:test_data.sql")
    public void findByProjectId()
    {
        List<Feature> features = repository.findByProject_ProjectID(1);
        assertThat(features.size(), is(1));
    }
}
