package com.example.darbslv.controller;

import com.example.darbslv.repository.JobOfferRepository;
import com.example.darbslv.service.JobAggregationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

class JobControllerTest {

    @Test
    void testShowJobsReturnsView() {

        JobAggregationService jobService = Mockito.mock(JobAggregationService.class);
        JobOfferRepository repo = Mockito.mock(JobOfferRepository.class);
        Model model = Mockito.mock(Model.class);

        JobController controller = new JobController(jobService, repo);

        String result = controller.jobs("all", null, model);

        assertThat(result).isEqualTo("jobs");
    }

    @Test
    void testRefreshCallsService() {
        JobAggregationService jobService = Mockito.mock(JobAggregationService.class);
        JobOfferRepository repo = Mockito.mock(JobOfferRepository.class);
        Model model = Mockito.mock(Model.class);

        JobController controller = new JobController(jobService, repo);

        String result = controller.refreshData(model);

        Mockito.verify(jobService).refreshAll();
        assertThat(result).isEqualTo("jobs");
    }
}
