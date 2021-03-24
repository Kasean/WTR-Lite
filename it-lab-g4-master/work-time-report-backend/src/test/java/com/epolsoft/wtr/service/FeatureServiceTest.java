package com.epolsoft.wtr.service;

import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.repository.FeatureRepository;
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
public class FeatureServiceTest {

    private static Feature p1;

    @Mock
    private FeatureRepository featureRepository;

    @InjectMocks
    private FeatureService featureService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Feature("Feature1");
        p1.setFeatureId(1);
    }
    
    @Test
    public void findByFeatureId() {
    	Mockito.when(featureRepository.findByFeatureId(1)).thenReturn(p1);
        assertThat(featureService.findById(1), is(p1));
        Mockito.verify(featureRepository, Mockito.times(1)).findByFeatureId(1);
    }

    @Test
    public void findByName() {
    	List<Feature> p = new ArrayList<>();
    	p.add(p1);
        Mockito.when(featureRepository.findByName("Feature1")).thenReturn(p);
        assertThat(featureService.findByName("Feature1"), is(p));
        Mockito.verify(featureRepository, Mockito.times(1)).findByName("Feature1");
    }
    @Test
    public void findByProjectId() {
        List<Feature> f = new ArrayList<>();
        f.add(p1);
        Mockito.when(featureRepository.findByProject_ProjectID(1)).thenReturn(f);
        assertThat(featureService.findByProjectId(1), is(f));
        Mockito.verify(featureRepository, Mockito.times(1)).findByProject_ProjectID(1);
    }
}
