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

    public void loadFakeJobs() {
        repository.deleteAll();
        for (int i = 1; i <= 5; i++) {
            JobOffer job = new JobOffer();
            job.setTitle("Java Developer " + i);
            job.setCompany("Company " + i);
            job.setLocation("Riga");
            job.setDescription("Test job offer " + i);
            job.setPublishedDate(LocalDate.now());
            repository.save(job);
        }
    }

    public List<JobOffer> getAllOffers() {
        return repository.findAll();
    }
}
