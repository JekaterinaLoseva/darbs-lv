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
        JobOffer job = JobOffer.builder()
                .title("QA Engineer")
                .company("Test Ltd")
                .sourceLink("https://example.com")
                .firstSeen(LocalDate.now())
                .lastSeen(LocalDate.now())
                .active(true)
                .build();

        repo.save(job);

        List<JobOffer> all = repo.findAll();

        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTitle()).isEqualTo("QA Engineer");
        assertThat(all.get(0).getCompany()).isEqualTo("Test Ltd");
    }

}
