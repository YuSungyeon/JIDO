package com.goorm.jido_.Entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
public class Category {
  @Id
  @Column(name = "category_id")
  private String categoryId; // 관심분야 카테고리 코드형 ID (예: a1.11.01)

  @Column(name = "name",  unique = true, nullable = false)
  private String name; // 카테고리명

  @Column(name = "depth", nullable = false)
  private int depth; // 계층 깊이

  @Column(name = "parent_category_id")
  private String parentCategoryId; // 상위 카테고리 ID (코드형)

  @Builder
  public Category(String categoryId, String name, int depth, String parentCategoryId) {
    this.categoryId = categoryId;
    this.name = name;
    this.depth = depth;
    this.parentCategoryId = parentCategoryId;
  }
}
