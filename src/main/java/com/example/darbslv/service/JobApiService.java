//package com.example.darbslv.service;
//
//import com.example.darbslv.model.JobOffer;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//public class JobApiService {
//
//    public List<JobOffer> loadFromApi() {
//        List<JobOffer> jobs = new ArrayList<>();
//
//        try {
//            String apiUrl = "https://www.visidarbi.lv/api/v1/vacancies/search?countries=LV&page=0&size=50";
//            log.info("Calling API: {}", apiUrl);
//
//            String jsonText = new String(new URL(apiUrl).openStream().readAllBytes(), StandardCharsets.UTF_8);
//
//            JSONObject json = new JSONObject(jsonText);
//            JSONArray content = json.getJSONArray("content");
//
//            for (int i = 0; i < content.length(); i++) {
//                JSONObject item = content.getJSONObject(i);
//
//                JobOffer job = new JobOffer();
//                job.setTitle(item.getString("title"));
//                job.setCompany(item.optString("companyName", "Nav norādīts"));
//                job.setLocation(item.optString("location", "Nav norādīts"));
//
//                job.setLink("https://www.visidarbi.lv/darba-sludinajums/" + item.getString("slug"));
//                job.setDescription(item.optString("salary", ""));
//
//                job.setPublishedDate(LocalDate.now());
//                jobs.add(job);
//            }
//
//            log.info("Loaded {} jobs", jobs.size());
//
//        } catch (IOException e) {
//            log.error("API load error: {}", e.getMessage());
//        }
//
//        return jobs;
//    }
//}
