package com.goorm.jido.controller;


import com.goorm.jido.dto.CategoryResponseDto;
import com.goorm.jido.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {
  private final CategoryService categoryService;


  @GetMapping("categories")
  public ResponseEntity<List<CategoryResponseDto>>  getCategories() {
    List<CategoryResponseDto> categories = categoryService.findAll()
            .stream()
            .map(CategoryResponseDto::new)
            .toList();

    return ResponseEntity.status(HttpStatus.OK)
                    .body(categories);
  }
}
