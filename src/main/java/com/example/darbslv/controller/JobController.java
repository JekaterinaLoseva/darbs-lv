package com.example.darbslv.controller;

import com.example.darbslv.service.RssJobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobController {

    private final RssJobService rssJobService;

    public JobController(RssJobService rssJobService) {
        this.rssJobService = rssJobService;
    }

    @GetMapping("/jobs")
    public String showJobs(Model model) {
        rssJobService.loadFakeJobs();
        model.addAttribute("jobs", rssJobService.getAllOffers());
        return "jobs";
    }
}
