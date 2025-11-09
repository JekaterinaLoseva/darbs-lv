package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RssJobServiceTest {

    @Autowired
    private RssJobService service;

    @Test
    void shouldReturnJobOffers() {
        List<JobOffer> jobs = service.getAllOffers();

        assertThat(jobs).isNotEmpty();
        assertThat(jobs.get(0).getTitle()).isNotBlank();
    }
}
