package com.epolsoft.wtr.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

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
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;

import com.epolsoft.wtr.entity.Factor;

//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SqlMergeMode(MergeMode.MERGE)
@Sql(scripts = "classpath:test_schema.sql",
   config = @SqlConfig(separator = "##") )

public class FactorRepositoryTest {

	private static Factor p1;

    @Autowired
    private FactorRepository repository;

    @BeforeAll
    public static void init() {
        p1 = new Factor();
        p1.setId(1);
        p1.setName("Factor1");
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findByName() {
    	List<Factor> p = new ArrayList<>();
    	p.add(p1);
        assertThat(repository.findByName("Factor1"), is(p));
    }

    @Test
    @Sql("classpath:test_data.sql")
    public void findAll() {
        List<Factor> iterableFactors = repository.findAll();
        long numberOfReports = StreamSupport.stream(iterableFactors.spliterator(), false).count();
        assertThat(numberOfReports, is(2L));
    }
    
    public void printReportStatus(List<Factor> result)
    {
        for(Factor s: result)
        {
        	System.out.println(s.toString());
        }   	
    }
}
