package com.epolsoft.wtr.controller;


import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.service.FeatureService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FeatureControllerTest {

    @InjectMocks
    FeatureController featureController;

    @Mock
    FeatureService featureService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddEmployee()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Feature feature = new Feature();
        feature.setFeatureId(1);

        when(featureService.createOrUpdate(any(Feature.class))).thenReturn(feature);

        Feature FeatureToAdd = new Feature();
        FeatureToAdd.setFeatureId(1);
        FeatureToAdd.setName("Feature1");
        ResponseEntity<Object> responseEntity = featureController.addFeature(FeatureToAdd);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");

    }



    @Test
    public void testFindAll()
    {
        // given
        Feature f1 = new Feature();
        f1.setFeatureId(1);
        f1.setName("Feature1");
        Feature f2 = new Feature();
        f2.setFeatureId(2);
        f2.setName("Feature2");
        List<Feature> list = new ArrayList<Feature>();
        list.addAll(Arrays.asList(f1, f2));

        when(featureService.findAll()).thenReturn(list);

        // when
        List<Feature> result = featureController.getAllFeatures();

        // then
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getName())
                .isEqualTo(f1.getName());

        assertThat(result.get(1).getName())
                .isEqualTo(f2.getName());

    }
    @Test
    public void testGetFeatureByProjectId()
    {
        Project p1 = new Project();
        p1.setProjectID(3);
        p1.setProjectName("Project3");
        Feature f1 = new Feature();
        f1.setFeatureId(1);
        f1.setName("Feature1");
        f1.setProject(p1);
        Feature f2 = new Feature();
        f2.setFeatureId(2);
        f2.setName("Feature2");
        f1.setProject(p1);
        List<Feature> features = new ArrayList<>();
        features.add(f1);
        features.add(f2);
        when(featureService.findByProjectId(any(Integer.class))).thenReturn(features);
        List<Feature> fs = featureController.getFeatureByProjectId(1);
        assertThat(fs.size()).isEqualTo(2);
    }

}

