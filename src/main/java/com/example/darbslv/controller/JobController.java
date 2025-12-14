package com.example.darbslv.controller;

import com.example.darbslv.repository.JobOfferRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobOfferRepository repo;

    public JobController(JobOfferRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String jobs(Model model) {
        model.addAttribute("jobs", repo.findAll());
        return "jobs";
    }
}
