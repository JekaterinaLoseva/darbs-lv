package com.example.darbslv.integration;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import com.example.darbslv.service.JobAggregationService;
import com.example.darbslv.service.JobSourceParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;


class AggregationIntegrationTest {

    @Test
    void aggregates_jobs_from_parsers_and_saves() {
        // given
        JobSourceParser parser = Mockito.mock(JobSourceParser.class);
        JobOfferRepository repo = Mockito.mock(JobOfferRepository.class);

        JobOffer job = JobOffer.builder()
                .title("QA Engineer")
                .company("Test Ltd")
                .location("Remote")
                .sourceLink("https://example.com/job")
                .firstSeen(LocalDate.now())
                .lastSeen(LocalDate.now())
                .active(true)
                .build();

        Mockito.when(parser.loadJobs()).thenReturn(List.of(job));

        JobAggregationService service =
                new JobAggregationService(List.of(parser), repo);

        // when
        service.aggregate();


        // then
        Mockito.verify(repo).save(Mockito.any(JobOffer.class));
    }

}
