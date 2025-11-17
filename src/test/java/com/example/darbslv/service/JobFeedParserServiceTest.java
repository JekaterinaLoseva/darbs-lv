package com.example.darbslv.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JobFeedParserServiceTest {

    @Test
    void testParserRuns() {
        JobFeedParserService service = new JobFeedParserService();
        List<?> jobs = service.loadAllJobs();
        assertNotNull(jobs);
    }
}
