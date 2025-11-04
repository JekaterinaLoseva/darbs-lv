package com.example.darbslv.service;

import com.example.darbslv.repository.JobOfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RssJobServiceTest {

    @Autowired
    private RssJobService service;

    @Autowired
    private JobOfferRepository repository;

    @Test
    void shouldLoadFakeJobs() {
        service.loadFakeJobs();
        assertThat(repository.findAll()).hasSize(5);
    }
}
