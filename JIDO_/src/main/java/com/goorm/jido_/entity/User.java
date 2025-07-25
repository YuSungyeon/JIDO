package com.goorm.jido_.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue

    private Long userId;
    private String nickname;
}
