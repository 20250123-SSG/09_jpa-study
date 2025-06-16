package com.podoseee.section03.join;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class JoinJPQLTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
    }

    @BeforeEach
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    /*
        ## 조인 종류 ##
        1. 일반 조인 : 일반적인 SQL 조인을 의미
        2. 페치 조인 : JPQL에서 성능 최적화를 위해 제공하는 기능
     */

    @Test
    public void 내부_조인_테스트() {
        // 연관관계 매핑 되어있지 않으면 아래 JPQL은 사용할 수 없습니다.
        // String jpql = "SELECT FROM menu3 m JOIN category3 c ON m.categoryCode = c.categoryCode";

        // 연관관계 매핑이 되어 있는 경우에는 이렇게 쿼리를 작성할 수 있습니다.
        String jpql = "SELECT m FROM Menu3 m JOIN m.category c";

        List<Menu3> menuList = entityManager.createQuery(jpql, Menu3.class).getResultList();

        menuList.forEach(System.out::println);
    }

    @AfterEach
    public void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void 외부_조인_테스트(){
        // 메뉴가 없는 카테고리도 조회
        String jpql = "SELECT m.menuName, c.categoryName FROM menu3 m RIGHT JOIN m.category c";

        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void 세타_조인_테스트(){
        // Theta 조인 : 조인 가능한 모든 경우를 조인하는 JPA 방식, 크로스조인(Cross Join)과 동일 개념
        String jpql = "SELECT FROM menu3 m, category3 c.categoryName FROM menu3 m, category3 c";

        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void 페치_조인_테스트(){

        String jpql = "SELECT m FROM menu3 m JOIN m.category";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        menuList.forEach(System.out::println);

        // 내부 조인으로 실행시 N + 1 문제 발생
        // 1. 최초로 내부 조인 SQL문 실행시 Menu 엔티티들의 정보 조회
        // 2. 각 Menu 엔티티별로 Category 엔티티를 조회하기 위해서 Menu 엔티티 개수만큼 Category 엔티티 조회 SQL문 실행(N번)

    }
}