package com.goorm.jido.dto;

import com.goorm.jido.entity.Category;

public record CategoryResponseDto(
        String categoryId,
        String name,
        int depth,
        String parentCategoryId
) {
  public CategoryResponseDto(Category category) {
    this(
            category.getCategoryId(),
            category.getName(),
            category.getDepth(),
            category.getParentCategoryId()
    );
  }
}