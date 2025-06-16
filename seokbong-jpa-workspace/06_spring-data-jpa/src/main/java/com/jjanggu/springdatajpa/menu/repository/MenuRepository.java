package com.jjanggu.springdatajpa.menu.repository;

import com.jjanggu.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
