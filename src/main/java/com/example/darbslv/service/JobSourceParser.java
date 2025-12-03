package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;

import java.util.List;

public interface JobSourceParser {
    List<JobOffer> loadJobs();
}
