package com.example.darbslv.repository;

import com.example.darbslv.model.JobOffer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql(statements = "DELETE FROM job_offer", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class JobOfferRepositoryTest {

    @Autowired
    private JobOfferRepository repository;

    @Test
    void shouldSaveAndFindAll() {
        JobOffer job = new JobOffer(null, "Test Job", "TestCompany",
                "https://example.com", "desc", "Riga", LocalDate.now());

        repository.save(job);
        List<JobOffer> jobs = repository.findAll();

        assertThat(jobs).isNotEmpty();
        assertThat(jobs.get(0).getTitle()).isEqualTo("Test Job");
    }
}