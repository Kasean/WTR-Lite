package com.epolsoft.wtr.repository;


import com.epolsoft.wtr.entity.Task;
import org.junit.BeforeClass;
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

import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;


//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql",
        config = @SqlConfig(separator = "##") )



public class TaskRepositoryTest {


    private static Task p1;
    private static Task p2;
    private static Task p1_modified;

    @Autowired
    private TaskRepository repository;

    @BeforeAll
    public static void init() {
        p1 = new Task(1, "task1");
        p2 = new Task(2, "task2");
        p1_modified = new Task(1,"task3");
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findById() {
        assertThat(repository.findOneById(1), is((p1)));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findByName() {
    	List<Task> p = new ArrayList<Task>();
    	p.add(p1);
        assertThat(repository.findByName("task1"), is((p)));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findAll() {
        List<Task> iterableTask = repository.findAll();
        assertThat(iterableTask.size(), is(2));
    }

    /*@Test
    @Sql("classpath:test_data.sql")
    public void update() {
        Task task = (Task) repository.findById(1);
        String newuserName = "userName";
        task.setName(newuserName);
        Task task1 = repository.save(task);
        assertNotNull(task1);
        assertThat(task1, is(task));

    }*/

    @Test
    @Sql("classpath:test_data.sql")
    public void count() {
        assertThat(repository.count(), is(2L));
    }

    public void printUserStatus()
    {
        Iterable<Task> result = repository.findAll();
        for(Task t: result)
        {
            System.out.println(t.toString());
        }
    }

}
