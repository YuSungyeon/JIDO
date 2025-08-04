package com.goorm.jido.dto;

import com.goorm.jido.entity.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto { // 모든 카테고리 조회
  private final String categoryId;
  private final String name;
  private final int depth;
  private final String parentCategoryId;

  public CategoryResponseDto(Category category) {
    this.categoryId = category.getCategoryId();
    this.name = category.getName();
    this.depth = category.getDepth();
    this.parentCategoryId = category.getParentCategoryId();
  }
}
