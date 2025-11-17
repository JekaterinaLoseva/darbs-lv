package com.example.darbslv.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "job_offer")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String link;

    @Column(length = 2000)
    private String description;

    private String location;
    private LocalDate publishedDate;

    public JobOffer(Long id, String title, String company, String link,
                    String description, String location, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.link = link;
        this.description = description;
        this.location = location;
        this.publishedDate = publishedDate;
    }
}
