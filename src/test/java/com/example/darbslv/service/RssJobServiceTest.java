package com.example.darbslv.service;

import com.example.darbslv.repository.JobOfferRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RssJobServiceTest {

    @Test
    void serviceCreatesInstance() {
        JobOfferRepository repo = Mockito.mock(JobOfferRepository.class);
        JobFeedParserService parser = Mockito.mock(JobFeedParserService.class);

        RssJobService service = new RssJobService(repo, parser);
    }
}
