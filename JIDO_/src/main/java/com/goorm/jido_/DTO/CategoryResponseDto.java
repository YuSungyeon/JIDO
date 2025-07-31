package com.goorm.jido_.DTO;

import com.goorm.jido_.Entitiy.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto { // 모든 카테고리 조회
  String categoryId;
  String name;
  int depth;
  String parentCategoryId;

  // Entity -> DTO
  public static CategoryResponseDto from(Category category) {
    return new CategoryResponseDto(
            category.getCategoryId(),
            category.getName(),
            category.getDepth(),
            category.getParentCategoryId()
    );
  }
}
