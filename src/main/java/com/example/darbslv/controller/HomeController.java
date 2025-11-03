package com.example.darbslv.controller;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.service.RssJobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final RssJobService rssJobService;

    public HomeController(RssJobService rssJobService) {
        this.rssJobService = rssJobService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<JobOffer> offers = rssJobService.loadJobs();
        model.addAttribute("offers", offers);
        return "index";
    }
}
