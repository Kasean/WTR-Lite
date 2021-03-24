package com.epolsoft.wtr.controller;


import com.epolsoft.wtr.entity.Report;
import com.epolsoft.wtr.service.ReportService;
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
public class ReportControllerTest {

    @InjectMocks
    ReportController reportController;

    @Mock
    ReportService reportService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void AddReport()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Report report = new Report();
        report.setReportId(1);

        when(reportService.createOrUpdate(any(Report.class))).thenReturn(report);

        Report reportToAdd = new Report(2);
        ResponseEntity<Object> responseEntity = reportController.addReport(reportToAdd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    public void FindAll()
    {
        Report report1 = new Report(1);
        Report report2 = new Report(2);

        List<Report> list = new ArrayList<Report>();
        list.addAll(Arrays.asList(report1, report2));

        when(reportService.findAll()).thenReturn(list);

        List<Report> result = reportController.getAllReport();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getReportId()).isEqualTo(report1.getReportId());
        assertThat(result.get(1).getReportId()).isEqualTo(report2.getReportId());
    }
}
