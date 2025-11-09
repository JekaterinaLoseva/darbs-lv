package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RssJobService {

    private final JobOfferRepository repository;

    public RssJobService(JobOfferRepository repository) {
        this.repository = repository;
    }

    public List<JobOffer> getAllOffers() {
        List<JobOffer> jobs = repository.findAll();

        if (jobs.isEmpty()) {
            JobOffer job1 = new JobOffer(null, "Java Developer", "Acme", "https://example.com/j1",
                    "Backend developer role", "Riga", LocalDate.now());
            JobOffer job2 = new JobOffer(null, "Frontend Engineer", "Beta", "https://example.com/j2",
                    "Frontend with React", "Liepaja", LocalDate.now());
            repository.saveAll(List.of(job1, job2));
            jobs = repository.findAll();
        }
        return jobs;
    }
}
