package com.example.darbslv.controller;

import com.example.darbslv.model.JobOffer;
import com.example.darbslv.repository.JobOfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock
    JobOfferRepository repo;

    @InjectMocks
    JobController controller;

    @Test
    void jobs_page_returns_jobs_list() {
        JobOffer job = JobOffer.builder()
                .title("QA Engineer")
                .company("Test Ltd")
                .location("Remote")
                .sourceLink("https://example.com/job")
                .firstSeen(LocalDate.now())
                .lastSeen(LocalDate.now())
                .active(true)
                .build();

        Mockito.when(repo.findAll()).thenReturn(List.of(job));

        Model model = Mockito.mock(Model.class);

        String view = controller.jobs(model);

        assertThat(view).isEqualTo("jobs");
        Mockito.verify(model).addAttribute("jobs", List.of(job));
    }
}
