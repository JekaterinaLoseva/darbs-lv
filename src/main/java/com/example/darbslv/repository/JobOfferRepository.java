package com.example.darbslv.repository;

import com.example.darbslv.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    JobOffer findByTitleAndCompany(String title, String company);

    List<JobOffer> findByActiveTrue();

    List<JobOffer> findByPublishedDateAfter(LocalDate date);

    List<JobOffer> findByTitleContainingIgnoreCase(String title);

    List<JobOffer> findByTitleContainingIgnoreCaseAndPublishedDateAfter(
            String title, LocalDate date
    );

}
