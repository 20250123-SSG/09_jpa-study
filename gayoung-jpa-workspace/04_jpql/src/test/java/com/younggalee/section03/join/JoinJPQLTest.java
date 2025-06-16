package com.younggalee.section03.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class JoinJPQLTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPA_Test");
    }

    @BeforeEach
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager() {
        entityManager.close();
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    /*
        ## 조인종류 ##
        1. 일반조인 : 일반적인 sql조인을 의미
        2. 패치조인 : jpql에서 성능 최적화를 위해 제공하는 기능
     */

    @Test
    public void 내부_조인_테스트() {
//        String jpql = "SELECT FROM menu3 m JOIN category3 c ON m.categoryCode = c.categoryCode"; //연관관계 매핑 되어있지 않을때
        String jpql = "SELECT m FROM menu3 m JOIN m.category"; //온절 필요 없이 가능 알아서 pk로 join됨

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        menuList.forEach(System.out::println);
    }



    @Test
    public void 외부_조인_테스트(){
        //메뉴가 없는 카테고리도 조회
        String jpql = "SELECT m.menuName, c.categoryName FROM menu3 m RIGHT JOIN m.category c";

        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    //JPQL 전용 조인
    @Test
    public void 세타_조인_테스트(){
        // Theta 조인 : 조인 가능한 모든 경우를 조인하는 jpa 방식, 크로스 조인과 동일 개념
        String jpql = "SELECT m.menuName, c.categoryName FROM menu3 m, category3 c";

        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void 패치_조인_테스트(){
        String jpql = "SELECT m FROM menu3 m JOIN m.category";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        menuList.forEach(System.out::println);

        // 내부 조인으로 실행시 N+1문제 발생  >> 패치조인 사용하는 이유
        // 1. 최초로 내부 조인 SQL문 실행시 메뉴 엔티티들의 정보 조회 (1회)
        // 2. 각 메뉴 엔티티별로 Category엠티티를 조회하기 위해서 Menu엔티티 개수만큼 Category 엔티티 조회 sql문 실행 (N번)
    }
}


