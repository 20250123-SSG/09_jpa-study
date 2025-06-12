package com.younggalee.springdatajpa.repository;

import com.younggalee.springdatajpa.menu.entity.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT category_code, category_name, ref_category_code FROM tbl_category WHERE ref_category_code IS NOT NULL", nativeQuery = true) // 기본값 false : JPQL
    List<Category> findAllSubCategory(); // 직접 작성하겠다
}
