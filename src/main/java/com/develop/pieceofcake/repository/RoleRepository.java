package com.develop.pieceofcake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop.pieceofcake.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    
}
