package com.example.darbslv.controller;

import com.example.darbslv.service.JobFeedParserService;
import com.example.darbslv.repository.JobOfferRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobControllerTest {

    @Test
    void testShowJobs() {

        JobOfferRepository repo = Mockito.mock(JobOfferRepository.class);
        JobFeedParserService parser = Mockito.mock(JobFeedParserService.class);

        JobController controller = new JobController(parser, repo);

        Model model = Mockito.mock(Model.class);

        String view = controller.showJobs(model);
        assertEquals("jobs", view);
    }
}
