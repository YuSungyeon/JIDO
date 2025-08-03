package com.goorm.jido_.service;

import com.goorm.jido_.entitiy.Category;
import com.goorm.jido_.repository.CategoryRepository;
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
