package com.example.darbslv.integration;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class JobsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @BeforeEach
    void setUp() {
        jobOfferRepository.deleteAll();

        JobOffer job = new JobOffer();
        job.setTitle("Junior Java Developer");
        job.setLink("https://example.com/jobs/1");
        job.setDescription("Test job description");
        job.setPublishedDate(LocalDate.now());
        jobOfferRepository.save(job);
    }

    @Test
    void jobsPageShouldLoadSuccessfully() throws Exception {
        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("jobs"));
    }

    @Test
    @WithMockUser
    void jobsPageShouldContainJobList() throws Exception {
        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(view().name("jobs"))
                .andExpect(model().attributeExists("jobs"))
                .andExpect(model().attribute("jobs", org.hamcrest.Matchers.hasSize(5)));
    }
}
