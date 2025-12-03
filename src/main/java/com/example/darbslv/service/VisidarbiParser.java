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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                List<Element> items = doc.select(
                        "div.item, div.item.premium, div.item.big-item");

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

                    if (!link.startsWith("http")) {
                        link = BASE_URL + link;
                    }

                    job.setTitle(title);
                    job.setCompany(item.select("li.company span").text());
                    job.setLocation(item.select("li.location span").text());
                    job.setSourceLink(link);   // источник сайта

                    Element dateEl = item.selectFirst("li.time span");
                    if (dateEl != null) {
                        job.setPublishedDate(parseDate(dateEl.text()));
                    } else {
                        job.setPublishedDate(LocalDate.now());
                    }

                    job.setFirstSeen(LocalDate.now());
                    job.setLastSeen(LocalDate.now());
                    job.setActive(true);

                    jobs.add(job);
                }
            }

            log.info("Loaded {} jobs from Visidarbi", jobs.size());

        } catch (Exception e) {
            log.error("Error in VisidarbiParser: {}", e.getMessage());
        }

        return jobs;
    }

    private LocalDate parseDate(String dateText) {
        LocalDate today = LocalDate.now();

        if (dateText == null || dateText.isEmpty())
            return today;

        if (dateText.contains("pirms")) {

            if (dateText.contains("stund"))
                return today;

            if (dateText.contains("minūt"))
                return today;

            if (dateText.contains("dien")) {
                Matcher m = Pattern.compile("(\\d+)").matcher(dateText);
                if (m.find()) {
                    int days = Integer.parseInt(m.group(1));
                    return today.minusDays(days);
                }
                return today.minusDays(1);
            }

            if (dateText.contains("nedēļ"))
                return today.minusWeeks(1);

            if (dateText.contains("mēnes"))
                return today.minusMonths(1);
        }

        return today;
    }
}
