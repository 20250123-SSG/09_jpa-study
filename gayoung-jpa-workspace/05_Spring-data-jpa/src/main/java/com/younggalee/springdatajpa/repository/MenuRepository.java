package com.younggalee.springdatajpa.repository;

import com.younggalee.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> { // <관리대상인 엔티티 타입, 식별자타입>  // interface가 interface 상속받아야할때
    // Spring Data JPA에서 Repository는 DAO역할을 함 (MyBatis의 Mapper Interface와 비슷한 역할)
    /*
    인터페이스 기반 : 둘 다 인터페이스로 정의하고, 구현 클래스는 프레임워크가 자동으로 생성함
    DB 접근 추상화 : SQL을 직접 작성하지 않아도 데이터베이스에 접근 가능
    DAO 역할     : 데이터를 조회/저장/삭제하는 역할 수행
     */

    @Query(value = "SELECT * FROM tbl_menu WHERE menu_price = ?1", nativeQuery = true) // 위치기반 - ?+숫자 / 이름기반 - : + 이름
    List<Menu> findByMenuPrice(int price); // 인자값 알아서 바인딩됨.  //   위치기반 - ?+숫자 / 이름기반 - : + 이름
    // findByMenuPriceEquals / findByMenuPriceGreaterThanEqual /쿼리메소드로도 실행가능
    // 정렬(Order By절)도 가능 : findByMenuPriceGreaterThanEqual(price, Sort.by("menuPrice").descending() >> SERVICCE
                                // findByMenuPriceGreaterThanEqual(int price, Sort sort)  >> repo
                            // 쿼리메소드 버전: findByMenuPriceGreaterThanEqualOrderByMenuPriceDesc(int price)

    //메소드명에 맞춰서 생성됨 : 쿼리메소드 "find + ([엔티티]) + By + 변수명 + 키워드" 규칙
    List<Menu> findByMenuNameContaining(String name);  //@Param("name") 생략가능

    List<Menu> findByMenuPriceGreaterThanEqualAndMenuNameContaining(int price, String name);
    // WHERE m.menuName LIKE '%마늘%'

}
