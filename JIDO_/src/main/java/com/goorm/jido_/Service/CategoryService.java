package com.goorm.jido_.Service;

import com.goorm.jido_.DTO.CategoryResponseDto;
import com.goorm.jido_.Entitiy.Category;
import com.goorm.jido_.Repository.CategoryRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
