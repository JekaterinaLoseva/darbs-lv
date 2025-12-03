package com.example.darbslv.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "job_offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String directLink;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    private LocalDate publishedDate;

    // New fields
    private LocalDate firstSeen;
    private LocalDate lastSeen;

    private String sourceLink;

    private boolean active;
}
