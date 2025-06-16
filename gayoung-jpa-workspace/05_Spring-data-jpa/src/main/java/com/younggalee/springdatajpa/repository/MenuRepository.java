package com.younggalee.springdatajpa.repository;

import com.younggalee.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> { // <관리대상인 엔티티 타입, 식별자타입>  // interface가 interface 상속받아야할때
    // Spring Data JPA에서 Repository는 DAO역할을 함 (MyBatis의 Mapper Interface와 비슷한 역할)
    /*
    인터페이스 기반 : 둘 다 인터페이스로 정의하고, 구현 클래스는 프레임워크가 자동으로 생성함
    DB 접근 추상화 : SQL을 직접 작성하지 않아도 데이터베이스에 접근 가능
    DAO 역할     : 데이터를 조회/저장/삭제하는 역할 수행
     */
}
