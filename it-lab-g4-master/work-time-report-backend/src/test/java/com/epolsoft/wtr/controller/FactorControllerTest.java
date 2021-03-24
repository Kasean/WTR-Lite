package com.epolsoft.wtr.controller;


import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.service.FactorService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FactorControllerTest {

    @InjectMocks
    FactorController factorController;

    @Mock
    FactorService factorService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void AddFactor()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Factor factor = new Factor();
        factor.setId(1);

        when(factorService.createOrUpdate(any(Factor.class))).thenReturn(factor);

        Factor reportToAdd = new Factor("Factor2");
        ResponseEntity<Object> responseEntity = factorController.addFactor(reportToAdd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    public void FindAll()
    {
        Factor factor1 = new Factor("Factor1");
        Factor factor2 = new Factor("Factor2");

        List<Factor> list = new ArrayList<Factor>();
        list.addAll(Arrays.asList(factor1, factor2));

        when(factorService.findAll()).thenReturn(list);

        List<Factor> result = factorController.getAllFactor();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(factor1.getId());
        assertThat(result.get(1).getId()).isEqualTo(factor2.getId());
    }
}
