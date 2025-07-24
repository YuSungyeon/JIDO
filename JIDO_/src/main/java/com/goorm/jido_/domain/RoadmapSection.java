package com.yourproject.domain.roadmap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RoadmapSection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long section_id;

    @ManyToOne
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    private String title;

    private Integer section_num;
}
