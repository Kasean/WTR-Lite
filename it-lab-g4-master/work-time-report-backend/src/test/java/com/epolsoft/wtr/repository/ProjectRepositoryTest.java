package com.epolsoft.wtr.repository;

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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql",
        config = @SqlConfig(separator = "##") )


public class ProjectRepositoryTest {


    private static Project p1;

    @Autowired
    private ProjectRepository repository;

    @BeforeAll
    public static void init() {
        p1 = new Project();
        p1.setProjectID(1);
        p1.setProjectName("Project1");
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findById() {
        assertThat(repository.findOneByProjectID(1), is(p1));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findAll() {
        List<Project> iterableProject = repository.findAll();
        long numberOfProject = iterableProject.size();
        assertThat(numberOfProject, is(2L));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findByName(){
        List<Project> projects = repository.findByName("Project1");
        assertThat(projects.size(), is(1));
        assertThat(projects.get(0).getProjectName(), is("Project1"));
    }
    
    @Test
    @Sql("classpath:test_data.sql")
    public void findByUserId(){
        List<Project> projects = repository.findByUsers_UserId(1);
        assertThat(projects.size(), is(1));
        assertThat(projects.get(0).getProjectName(), is("Project1"));
    }

}
