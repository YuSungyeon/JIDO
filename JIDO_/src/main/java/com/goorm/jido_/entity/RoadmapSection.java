package com.goorm.jido_.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roadmap_section")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    private String title;

    private Integer sectionNum;
}
