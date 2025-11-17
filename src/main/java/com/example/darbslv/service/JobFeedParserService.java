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
public class JobFeedParserService {

    private static final String BASE_URL = "https://www.visidarbi.lv";
    private static final String PAGE_URL = BASE_URL + "/darba-sludinajumi?page=";

    public List<JobOffer> loadAllJobs() {
        List<JobOffer> jobs = new ArrayList<>();

        try {
            for (int page = 1; page <= 10; page++) {

                String url = PAGE_URL + page;
                log.info("Fetching {}", url);

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(15000)
                        .get();

                List<Element> items =
                        doc.select("div.item, div.item.premium, div.item.big-item");

                if (items.isEmpty()) {
                    log.info("No items found — stopping");
                    break;
                }

                for (Element item : items) {
                    JobOffer job = new JobOffer();

                    Element titleEl = item.selectFirst(".title h3 a");
                    if (titleEl == null) continue;

                    String title = titleEl.text();
                    String link = titleEl.attr("href");

                    if (!link.startsWith("http"))
                        link = BASE_URL + link;

                    job.setTitle(title);
                    job.setLink(link);

                    job.setCompany(item.select("li.company span").text());
                    job.setLocation(item.select("li.location span").text());
                    job.setPublishedDate(LocalDate.now());

                    jobs.add(job);
                }
            }

            log.info("Loaded {} jobs", jobs.size());

        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }

        return jobs;
    }
}
