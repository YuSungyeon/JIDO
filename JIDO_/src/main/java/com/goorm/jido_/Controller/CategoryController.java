package com.goorm.jido_.Controller;


import com.goorm.jido_.DTO.CategoryResponseDto;
import com.goorm.jido_.Repository.CategoryRepository;
import com.goorm.jido_.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {
  private final CategoryService categoryService;


  @GetMapping("categories")
  public ResponseEntity<List<CategoryResponseDto>>  getCategories() {
    return ResponseEntity.status(HttpStatus.OK)
                    .body(categoryService.findAll());
  }
}
