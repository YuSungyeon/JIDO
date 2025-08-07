package com.goorm.jido.repository;

import com.goorm.jido.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{
  Optional<Category> findByCategoryId(String categoryId);
}