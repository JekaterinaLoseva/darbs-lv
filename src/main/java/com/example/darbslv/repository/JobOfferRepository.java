package com.example.darbslv.repository;

import com.example.darbslv.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByTitleContainingIgnoreCase(String keyword);
}
