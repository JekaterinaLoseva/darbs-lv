package com.example.darbslv.repository;

import com.example.darbslv.model.JobOffer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class JobOfferRepositoryTest {

    @Autowired
    private JobOfferRepository repo;

    @Test
    void testSaveAndFind() {
        JobOffer job = new JobOffer();
        job.setTitle("Test Job");
        job.setCompany("CompanyA");
        job.setSourceLink("https://example.com");
        job.setDirectLink("https://example.com");
        job.setFirstSeen(LocalDate.now());
        job.setLastSeen(LocalDate.now());
        job.setActive(true);

        repo.save(job);

        List<JobOffer> all = repo.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTitle()).isEqualTo("Test Job");
    }
}
