package com.develop.pieceofcake.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop.pieceofcake.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findAllByCategory_Id(int id);
    
}
