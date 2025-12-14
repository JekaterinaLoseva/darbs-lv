package com.example.darbslv.service;

import com.example.darbslv.model.JobOffer;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VisidarbiParser implements JobSourceParser {

    private static final String BASE_URL = "https://www.visidarbi.lv";
    private static final String PAGE_URL = BASE_URL + "/darba-sludinajumi?page=";

    @Override
    public List<JobOffer> loadJobs() {
        List<JobOffer> jobs = new ArrayList<>();

        try {
            for (int page = 1; page <= 10; page++) {
                String url = PAGE_URL + page;
                log.info("Fetching {}", url);

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(15000)
                        .get();

                for (Element item : doc.select("div.item, div.item.premium, div.item.big-item")) {

                    Element titleEl = item.selectFirst(".title h3 a");
                    if (titleEl == null) continue;

                    String link = titleEl.attr("href");
                    if (!link.startsWith("http")) {
                        link = BASE_URL + link;
                    }

                    JobOffer job = JobOffer.builder()
                            .title(titleEl.text())
                            .company(item.select("li.company span").text())
                            .location(item.select("li.location span").text())
                            .sourceLink(link)
                            .firstSeen(LocalDate.now())
                            .lastSeen(LocalDate.now())
                            .active(true)
                            .build();

                    jobs.add(job);
                }
            }

            log.info("Loaded {} jobs from Visidarbi", jobs.size());

        } catch (Exception e) {
            log.error("Visidarbi parser failed", e);
        }

        return jobs;
    }
}
