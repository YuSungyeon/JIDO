package com.goorm.jido_.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roadmap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roadmapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author; // User 테이블과 연관관계 필요하면 @ManyToOne 추가 가능

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(nullable = false)
    private Boolean is_public = true;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL)
    private List<RoadmapSection> sections;
}
