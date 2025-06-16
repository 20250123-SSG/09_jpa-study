package com.ino.springdatajpa.menu.repository;

import com.ino.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> { // 대상으로 삼을 <객체 타입, 객체의 pk 타입>
}
