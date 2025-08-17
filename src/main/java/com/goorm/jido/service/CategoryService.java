package com.goorm.jido.service;

import com.goorm.jido.entity.Category;
import com.goorm.jido.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> findAll() { // 모든 카테고리 반환
    return categoryRepository.findAll();
  }
}
