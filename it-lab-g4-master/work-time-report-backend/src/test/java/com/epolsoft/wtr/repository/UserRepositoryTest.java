package com.epolsoft.wtr.repository;

import com.epolsoft.wtr.entity.User;
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

import java.util.Optional;
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


public class UserRepositoryTest {

    private static User p1;
    private static User p2;
    private static User p1_modified;

    @Autowired
    private UserRepository repository;

    @BeforeAll
    public static void init() {
        p1 = new User(1,"User1", "pass1");
        p2 = new User();
        p1_modified = new User();
        //p1 = new User( 1, "1");
        //p2 = new User( 2,  "2");
        //p1_modified = new User( 1,  "3");
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findById() {
        assertThat(repository.findById(1), is(Optional.of(p1)));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findAll() {
        Iterable<User> iterableUser = repository.findAll();
        long numberOfUser = StreamSupport.stream(iterableUser.spliterator(), false).count();
        assertThat(numberOfUser, is(2L));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void update() {
        User user = repository.findByUserId(1);
        String newuserName = "userName";
        user.setUserName(newuserName);
        User user1 = repository.save(user);
        assertNotNull(user1);
        assertThat(user1, is(user));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void count() {
        assertThat(repository.count(), is(2L));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findByName() {
        assertThat(repository.findByName("User1"), is(p1));
    }

    public void printUserStatus()
    {
        Iterable<User> result = repository.findAll();
        for(User s: result)
        {
            System.out.println(s.toString());
        }
    }
}
