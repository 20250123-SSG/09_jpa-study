package com.jjanggu.section01.simple;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

public class SimpleJPQLTest {

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
    public void 단일_메뉴명_조회_테스트(){

        String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode = 7";

        // 1) TypedQuery
        /*
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        String menuName = query.getSingleResult();
         */

        // 2) Query
        Query query = entityManager.createQuery(jpql);
        String menuName = (String)query.getSingleResult();

        System.out.println(menuName);

    }

    @Test
    public void 메뉴_단일행_조회_테스트(){
        String jpql = "SELECT m FROM menu1 m WHERE m.menuCode = 7";

        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu foundMenu = query.getSingleResult();

        System.out.println(foundMenu);
    }

    @Test
    public void 메뉴_다중행_조회_테스트(){
        String jpql = "SELECT m FROM menu1 m";

        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> menuList = query.getResultList();

        menuList.forEach(System.out::println);
    }

    @Test
    public void DISTINCT_활용_테스트(){
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu1 m";

        List<Integer> categoryList =  entityManager.createQuery(jpql, Integer.class).getResultList();

        categoryList.forEach(System.out::println);
    }

    @Test
    public void IN_활용_테스트(){
        String jpql ="SELECT m FROM menu1 m WHERE m.categoryCode IN(4, 6)";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        menuList.forEach(System.out::println);
    }

    @Test
    public void LIKE_활용_테스트(){
        String jpql = "SELECT m FROM menu1 m WHERE m.menuName LIKE '%마늘%'";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        menuList.forEach(System.out::println);
    }

    /*
        ## 파라미터 바인딩 ##
        1. 이름 기준 바인딩 (named parameters)
           ':' 뒤에 파라미터명을 부여한 뒤 사용
        2. 위치 기준 바인딩 (positional parameters)
           '?' 뒤에 위치를 부여한 뒤 사용
     */

    @Test
    public void 이름_기준_파라미터_바인딩_테스트(){
        String jpql = "SELECT m FROM menu1 m WHERE m.menuCode = :code";

        Integer menuCode = 8;

        Menu foundMenu = entityManager.createQuery(jpql, Menu.class)
                                      .setParameter("code", menuCode)
                                      .getSingleResult();

        System.out.println(foundMenu);
    }

    @Test
    public void 위치_기준_파라미터_바인딩_테스트(){

        // 사용자 입력값 (전달값)
        Integer categoryCode = 4;
        Integer menuPrice = 13000;

        // 파라미터 바인딩 x
        /*
        String jpql = "SELECT m FROM menu1 m WHERE m.categoryCode =" + categoryCode + "AND .menuPrice = " + menuPrice;


         */

        // 파라미터 바인딩 o
        String jpql = "SELECT m FROM menu1 m WHERE m.categoryCode = ?1 AND m.menuPrice > ?2";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setParameter(1, categoryCode)
                .setParameter(2, menuPrice)
                .getResultList();

        for (Menu menu : menuList){
            System.out.println(menu);
        }
    }

    @Test
    public void LIMIT_활용_테스트(){

        int offset = 0;
        int display = 10;

        String jpql = "SELECT m FROM menu1 m ORDER BY m.menuCode DESC";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                                           .setFirstResult(offset) // LIMIT 절의 offset
                                           .setMaxResults(display) // LIMIT 절의 display
                                           .getResultList();

        menuList.forEach(System.out::println);

    }

    /*
        ## 그룹 함수 (COUNT, MAX, SUM, AVG) ##
        1. 그룹 함수의 반환 타입은 정수값일 경우 Long, 실수값일 경우 Double
        2. 값이 없는 상태에서 COUNT의 결과는 0, COUNT 제외 함수의 결과는 null
           => 반환 값을 담기 위한 변수 선언시 기본자료형으로 하게 되면 언박싱(Wrapper=>Primitive) 시 NPE 발생 여지가 있음
     */

    @Test
    public void 통계홤수_COUNT_테스트(){
        String jpql = "SELECT COUNT(m.menuCode) FROM menu1 m WHERE m.categoryCode = ?1";

        Integer categoryCode = 1;

        long numOfRows =  entityManager.createQuery(jpql, Long.class)
                                       .setParameter(1, categoryCode)
                                       .getSingleResult();

        System.out.println(numOfRows);

        /*
            COUNT 특징
            1. 반환 타입 Long
            2. 데이터가 없는 경우 0을 반환
            3. 최종 결과를 기록할 변수의 타입은 Long, long(primitive type)을 사용해서 상관 없음
         */

    }

    @Test
    public void 통계함수_COUNT외_테스트(){

        String jpql = "SELECT SUM(m.menuPrice) FROM menu1 m WHERE m.categoryCode = ?1";

        Integer categoryCode = 1;

        long totalPrice = entityManager.createQuery(jpql, Long.class)
                .setParameter(1, categoryCode)
                .getSingleResult();

        System.out.println(totalPrice);

        /*
            SUM, AVG, MAX, MIN
            1. 반환 타입이 Long 또는 Double
            2. 데이터가 없을 경우 null 반환
            3. 최종 결과를 담는 변수를 기본자료형으로 두게 되면 NPE 발생 여지 있음
         */

    }


}
