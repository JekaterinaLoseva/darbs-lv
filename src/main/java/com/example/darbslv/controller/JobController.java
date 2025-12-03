package com.example.darbslv.controller;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import com.example.darbslv.service.JobAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class JobController {

    private final JobAggregationService jobService;
    private final JobOfferRepository jobRepo;

    @GetMapping("/jobs")
    public String jobs(
            @RequestParam(value = "range", defaultValue = "all") String range,
            @RequestParam(value = "query", required = false) String query,
            Model model) {

        List<JobOffer> jobs;
        LocalDate now = LocalDate.now();
        LocalDate fromDate = null;

        switch (range) {
            case "day":
                fromDate = now;
                break;
            case "week":
                fromDate = now.minusDays(7);
                break;
            case "month":
                fromDate = now.minusDays(30);
                break;
            case "all":
            default:
                fromDate = null;
                break;
        }

        if (query != null && !query.isEmpty()) {

            if (fromDate != null) {
                jobs = jobRepo
                        .findByTitleContainingIgnoreCaseAndPublishedDateAfter(query, fromDate);
            } else {
                jobs = jobRepo
                        .findByTitleContainingIgnoreCase(query);
            }

        } else if (fromDate != null) {
            jobs = jobRepo.findByPublishedDateAfter(fromDate);
        } else {
            jobs = jobRepo.findAll();
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("range", range);
        model.addAttribute("query", query);

        return "jobs";
    }

    @GetMapping("/refresh")
    public String refreshData(Model model) {

        jobService.refreshAll(); // новая логика агрегации

        List<JobOffer> jobs = jobRepo.findAll();

        model.addAttribute("jobs", jobs);
        model.addAttribute("message", "Dati atjaunoti!");

        return "jobs";
    }
}
