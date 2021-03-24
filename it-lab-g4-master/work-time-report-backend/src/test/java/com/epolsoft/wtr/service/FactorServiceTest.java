package com.epolsoft.wtr.service;

import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.repository.FactorRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@Disabled("Disabled!") // нужно раскомитить если вы не хотите запускать эти тесты
@ExtendWith(MockitoExtension.class)
public class FactorServiceTest {

    private static Factor p1;

    @Mock
    private FactorRepository factorRepository;

    @InjectMocks
    private FactorService factorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Factor("Factor1");
        p1.setId(1);
    }
    
    @Test
    public void findByFactorId() {
    	Mockito.when(factorRepository.findOneById(1)).thenReturn(p1);
        assertThat(factorService.findById(1), is(p1));
        Mockito.verify(factorRepository, Mockito.times(1)).findOneById(1);
    }

    @Test
    public void findByName() {
    	List<Factor> p = new ArrayList<>();
    	p.add(p1);
        Mockito.when(factorRepository.findByName("Factor1")).thenReturn(p);
        assertThat(factorService.findByName("Factor1"), is(p));
        Mockito.verify(factorRepository, Mockito.times(1)).findByName("Factor1");
    }
}
