package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobAggregationService {

    private final List<JobSourceParser> parsers;
    private final JobOfferRepository jobRepo;
    private final OriginalLinkResolver linkResolver;

    public void refreshAll() {
        log.info("Starting job aggregation…");

        List<JobOffer> aggregated = new ArrayList<>();

        for (JobSourceParser parser : parsers) {
            try {
                List<JobOffer> parsed = parser.loadJobs();
                aggregated.addAll(parsed);
                log.info("Loaded {} jobs from {}", parsed.size(), parser.getClass().getSimpleName());
            } catch (Exception e) {
                log.error("Parser {} failed: {}", parser.getClass().getSimpleName(), e.getMessage());
            }
        }

        log.info("Total aggregated jobs before dedupe: {}", aggregated.size());

        for (JobOffer job : aggregated) {
            processJob(job);
        }

        markInactiveJobs(aggregated);

        log.info("Aggregation finished.");
    }

    private void processJob(JobOffer incoming) {
        try {
            String direct = linkResolver.resolve(incoming.getSourceLink());
            incoming.setDirectLink(direct);

            JobOffer existing = jobRepo.findByTitleAndCompany(
                    incoming.getTitle(),
                    incoming.getCompany()
            );

            if (existing == null) {
                incoming.setFirstSeen(LocalDate.now());
                incoming.setLastSeen(LocalDate.now());
                incoming.setActive(true);

                jobRepo.save(incoming);
                log.info("Added NEW job: {} / {}", incoming.getTitle(), incoming.getCompany());

            } else {
                existing.setLastSeen(LocalDate.now());
                existing.setActive(true);

                existing.setDirectLink(direct);
                existing.setLocation(incoming.getLocation());
                existing.setPublishedDate(incoming.getPublishedDate());
                existing.setSourceLink(incoming.getSourceLink());

                jobRepo.save(existing);
                log.info("Updated existing job: {} / {}", existing.getTitle(), existing.getCompany());
            }

        } catch (Exception e) {
            log.error("Error processing job {}: {}", incoming.getTitle(), e.getMessage());
        }
    }

    private void markInactiveJobs(List<JobOffer> newJobs) {

        List<JobOffer> all = jobRepo.findAll();

        for (JobOffer job : all) {

            boolean exists = newJobs.stream().anyMatch(n ->
                    n.getTitle().equals(job.getTitle()) &&
                            n.getCompany().equals(job.getCompany())
            );

            if (!exists && job.isActive()) {
                job.setActive(false);
                jobRepo.save(job);
                log.info("Marked inactive: {} / {}", job.getTitle(), job.getCompany());
            }
        }
    }

    public List<JobOffer> getActiveJobs() {
        return jobRepo.findByActiveTrue();
    }

    public List<JobOffer> getAllJobs() {
        return jobRepo.findAll();
    }
}
