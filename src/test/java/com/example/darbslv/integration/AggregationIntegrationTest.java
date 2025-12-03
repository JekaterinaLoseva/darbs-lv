package com.example.darbslv.integration;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import com.example.darbslv.service.JobAggregationService;
import com.example.darbslv.service.JobSourceParser;
import com.example.darbslv.service.OriginalLinkResolver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AggregationIntegrationTest {

    @Autowired
    private JobOfferRepository repo;

    @Test
    void testAggregationFlow() {

        JobSourceParser fakeParser = () -> {
            JobOffer job = new JobOffer();
            job.setTitle("QA Engineer");
            job.setCompany("AutoCorp");
            job.setSourceLink("https://example.com/job/qa");
            job.setDirectLink("https://example.com/job/qa");
            job.setFirstSeen(LocalDate.now());
            job.setLastSeen(LocalDate.now());
            job.setActive(true);
            return List.of(job);
        };

        OriginalLinkResolver resolver = Mockito.mock(OriginalLinkResolver.class);
        Mockito.when(resolver.resolve(Mockito.anyString())).thenReturn("https://final-link.com");

        JobAggregationService service =
                new JobAggregationService(List.of(fakeParser), repo, resolver);

        service.refreshAll();

        List<JobOffer> all = repo.findAll();
        assertThat(all).hasSize(1);

        JobOffer saved = all.get(0);

        assertThat(saved.getTitle()).isEqualTo("QA Engineer");
        assertThat(saved.getCompany()).isEqualTo("AutoCorp");
        assertThat(saved.getDirectLink()).isEqualTo("https://final-link.com");
        assertThat(saved.isActive()).isTrue();
    }
}
