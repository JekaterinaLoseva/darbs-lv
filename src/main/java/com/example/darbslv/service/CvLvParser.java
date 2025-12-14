package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CvLvParser implements JobSourceParser {

    private static final String SEARCH_URL =
            "https://cv.lv/api/v1/vacancy-search-service/search";

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public CvLvParser(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<JobOffer> loadJobs() {
        log.info("=== CV.lv PARSER START ===");

        List<JobOffer> jobs = new ArrayList<>();

        int limit = 50;
        int offset = 0;

        while (true) {
            try {
                URI uri = UriComponentsBuilder
                        .fromHttpUrl(SEARCH_URL)
                        .queryParam("limit", limit)
                        .queryParam("offset", offset)
                        .queryParam("categories", "INFORMATION_TECHNOLOGY")
                        .queryParam("keywords", "junior")
                        .build()
                        .toUri();

                String json = restTemplate.getForObject(uri, String.class);
                JsonNode root = mapper.readTree(json);
                JsonNode items = root.path("vacancies").path("items");

                if (!items.isArray() || items.isEmpty()) {
                    log.info("No more items from CV.lv");
                    break;
                }

                for (JsonNode it : items) {
                    JobOffer job = JobOffer.builder()
                            .title(it.path("position").asText())
                            .company(it.path("company").path("name").asText())
                            .sourceLink(it.path("links").path("detail").asText())
                            .active(true)
                            .firstSeen(LocalDate.now())
                            .lastSeen(LocalDate.now())
                            .build();

                    jobs.add(job);
                    log.info("CV.lv job: {} / {}", job.getTitle(), job.getCompany());
                }

                offset += limit;

            } catch (Exception e) {
                log.error("CV.lv parser failed", e);
                break;
            }
        }

        log.info("Loaded {} jobs from CV.lv", jobs.size());
        return jobs;
    }
}
