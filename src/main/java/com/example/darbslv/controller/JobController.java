package com.example.darbslv.controller;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.service.JobFeedParserService;
import com.example.darbslv.repository.JobOfferRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class JobController {

    private final JobFeedParserService parser;
    private final JobOfferRepository repo;

    public JobController(JobFeedParserService parser, JobOfferRepository repo) {
        this.parser = parser;
        this.repo = repo;
    }


    @GetMapping("/jobs")
    public String showJobs(Model model) {
        model.addAttribute("jobs", repo.findAll());
        return "jobs";
    }

    @GetMapping("/refresh")
    public String refresh(Model model) {

        repo.deleteAll();

        List<JobOffer> jobs = parser.loadAllJobs();
        repo.saveAll(jobs);

        model.addAttribute("jobs", jobs);
        model.addAttribute("message", "Atjaunoti dati (" + jobs.size() + ")");

        return "jobs";
    }
}
