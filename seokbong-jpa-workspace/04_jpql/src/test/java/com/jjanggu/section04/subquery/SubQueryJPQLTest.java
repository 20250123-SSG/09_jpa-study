package com.jjanggu.section04.subquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

public class SubQueryJPQLTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory(){entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");}
    @BeforeEach
    public void initEntityManager(){entityManager = entityManagerFactory.createEntityManager();}
    @AfterEach
    public void destroyEntityManager(){entityManager.close();}
    @AfterAll
    public static void destroyEntityManagerFactory(){ entityManagerFactory.close(); }

    @Test
    public void 서브쿼리_테스트(){
        // 카테고리명이 "한식"인 메뉴를 조회하기
        String jpql = """
                SELECT m
                  FROM menu4 m
                 WHERE m.categoryCode = (SELECT c.categoryCode
                                           FROM category4 c
                                          WHERE c.categoryName = '한식')""";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        menuList.forEach(System.out::println);
    }

    @Test
    public void 동적쿼리_테스트(){
        // 검색 기능
        // 메뉴명 키워드와 카테고리 코드로 필터링 검색 기능이 있다고 가정
        // 단, 키워드와 카테고리 코드는 입력될 수도 있고 안될 수도 있다고 가정

        //                      전체조회        키워드 검색만         카테고리검색만         둘다
        String searchKeyword = "마늘";        //      "마늘"               "" | null          "마늘"
        int searchCategoryCode = 4;         //        0                     4                 4

        StringBuilder jpql = new StringBuilder("SELECT m FROM menu4 m");

        if(searchKeyword != null && !"".equals(searchKeyword) && searchCategoryCode != 0){
            jpql.append(" WHERE m.menuName LIKE '%' || :keyword || '%'");
            jpql.append("  AND m.categoryCode = :category");
        }else {
            if(searchKeyword != null && !"".equals(searchKeyword)){
                jpql.append(" WHERE m.menuName LIKE '%' || :keyword || '%'");
            }else if(searchCategoryCode != 0){
                jpql.append(" WHERE m.categoryCode = :category");
            }
        }

        TypedQuery<Menu> query = entityManager.createQuery(jpql.toString(), Menu.class);
        if(searchKeyword != null && !"".equals(searchKeyword)){
            query.setParameter("keyword", searchKeyword);
        }

        if(searchCategoryCode != 0){
            query.setParameter("category", searchCategoryCode);
        }

        List<Menu> menuList = query.getResultList();

        menuList.forEach(System.out::println);

    }
}
