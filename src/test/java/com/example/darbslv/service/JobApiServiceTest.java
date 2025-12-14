//package com.example.darbslv.service;
//
//import com.example.darbslv.model.JobOffer;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JobApiServiceTest {
//
//    @Test
//    void testLoadFromApiReturnsList() {
//        JobApiService service = new JobApiService() {
//            @Override
//            public List<JobOffer> loadFromApi() {
//                JobOffer job = new JobOffer();
//                job.setTitle("Test Job");
//                job.setCompany("Test Company");
//                return List.of(job);
//            }
//        };
//
//        List<JobOffer> jobs = service.loadFromApi();
//
//        assertNotNull(jobs);
//        assertEquals(1, jobs.size());
//        assertEquals("Test Job", jobs.get(0).getTitle());
//    }
//}
