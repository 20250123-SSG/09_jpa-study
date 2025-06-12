package com.ino.springdatajpa.menu.repository;

import com.ino.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT category_code, category_name, ref_category_code FROM tbl_category WHERE ref_category_code IS NOT NULL", nativeQuery = true) // JPQL, native Query 상관 x, 기본적으로 JPQL 인식.
    List<Category> findAllSubCategory();

    List<Category> findByRefCategoryCodeIsNotNullOrderByCategoryCodeDesc();
}
