package com.ino.springdatajpa.menu.repository;

import com.ino.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> { // 대상으로 삼을 <객체 타입, 객체의 pk 타입>

//    @Query(value = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status FROM tbl_menu WHERE menu_price = ?1", nativeQuery = true)
//    List<Menu> findByMenuPrice(int price); // position based mapping -> parameter position auto-mapping

//    @Query(value = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status FROM tbl_menu WHERE menu_price = :price", nativeQuery = true)
//    List<Menu> findByMenuPrice(@Param("price") int price); // name based binding -> name이 같을 경우 생략 가능

    List<Menu> findMenuByMenuPrice(Integer menuPrice);

    List<Menu> findMenuByMenuPriceGreaterThanEqual(Integer menuPriceIsGreaterThan, Sort sort);

    List<Menu> findMenuByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(Integer menuPriceIsGreaterThan);


    List<Menu> findMenuByMenuNameContaining(String name);

    List<Menu> findMenuByMenuPriceGreaterThanAndMenuNameContaining(Integer menuPriceIsGreaterThan, String menuName);
}
