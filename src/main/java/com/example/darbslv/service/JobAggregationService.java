package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobAggregationService {

    private final List<JobSourceParser> parsers;
    private final JobOfferRepository jobRepo;

    public void aggregate() {
        log.info("Starting job aggregation");

        for (JobSourceParser parser : parsers) {
            try {
                log.info("Running parser: {}", parser.getClass().getSimpleName());

                List<JobOffer> jobs = parser.loadJobs();
                log.info("Loaded {} jobs", jobs.size());

                for (JobOffer job : jobs) {
                    jobRepo.save(job);
                }

            } catch (Exception e) {
                log.error("Parser {} failed", parser.getClass().getSimpleName(), e);
            }
        }

        log.info("Job aggregation finished");
    }
}
