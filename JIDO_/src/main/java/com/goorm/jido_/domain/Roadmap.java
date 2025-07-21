package com.goorm.jido_.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Roadmap {
    @Id
    @GeneratedValue

    private Long roadmapId;
    private String title;
}
