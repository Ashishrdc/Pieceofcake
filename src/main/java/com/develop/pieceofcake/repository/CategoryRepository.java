package com.develop.pieceofcake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop.pieceofcake.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
