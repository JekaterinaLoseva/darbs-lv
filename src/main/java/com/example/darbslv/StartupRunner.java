package com.example.darbslv;

import com.example.darbslv.service.JobAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

    private final JobAggregationService aggregationService;

    @Override
    public void run(String... args) {
        aggregationService.aggregate();
    }
}
