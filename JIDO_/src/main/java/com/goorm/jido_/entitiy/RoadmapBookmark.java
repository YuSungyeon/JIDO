package com.goorm.jido_.entitiy;


import com.goorm.jido_.entitiy.roadmap.Roadmap;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "roadmap_bookmark")
public class RoadmapBookmark {

  @Id
  @Column(name = "bookmark_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // 스크랩 고유 id

  @ManyToOne
  @JoinColumn(name = "roadmap_id", nullable = false)
  private Roadmap roadmap; // 참조 로드맵

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}
