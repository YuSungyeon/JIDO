package com.yourproject.domain.roadmap.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Step")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long step_id;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private RoadmapSection section;

    private String title;

    private Integer step_number;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    private List<StepContent> contents;
}
