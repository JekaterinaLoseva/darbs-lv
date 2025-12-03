package com.example.darbslv.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OriginalLinkResolver {

    public String resolve(String url) {
        try {
            log.info("Resolving direct link for {}", url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(15000)
                    .followRedirects(true)
                    .get();

            String finalUrl = doc.location();

            Element cvlvBtn = doc.selectFirst("a.button--primary[href*=company]");
            if (cvlvBtn != null) {
                String href = cvlvBtn.attr("href");
                log.info("Found CV.lv company link: {}", href);
                return href;
            }

            Element apply = doc.selectFirst("a:matchesOwn((Apply|Pieteikties))");
            if (apply != null && apply.attr("href").startsWith("http")) {
                log.info("Found Apply link: {}", apply.attr("href"));
                return apply.attr("href");
            }

            Element originalBtn = doc.selectFirst("a.button[href*='http']");
            if (originalBtn != null) {
                String href = originalBtn.attr("href");
                if (href.startsWith("http")) {
                    log.info("Found direct link inside Visidarbi: {}", href);
                    return href;
                }
            }

            return finalUrl;

        } catch (Exception e) {
            log.error("Error resolving link {}: {}", url, e.getMessage());
            return url; // fallback
        }
    }
}
