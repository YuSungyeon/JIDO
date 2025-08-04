package com.goorm.jido_.dto;

import com.goorm.jido_.entity.Category;

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