package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RssJobService {

    private final JobOfferRepository jobRepo;
    private final JobFeedParserService parser;

    public void refreshDatabase() {
        jobRepo.deleteAll();
        List<JobOffer> fresh = parser.loadAllJobs();
        jobRepo.saveAll(fresh);
    }

    public List<JobOffer> getAllJobs() {
        return jobRepo.findAll();
    }
}
