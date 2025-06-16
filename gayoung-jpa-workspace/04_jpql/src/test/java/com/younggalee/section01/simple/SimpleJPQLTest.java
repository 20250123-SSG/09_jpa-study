package com.younggalee.section01.simple;

import jakarta.persistence.*;
import jdk.jfr.Category;
import org.assertj.core.util.introspection.Introspection;
import org.junit.jupiter.api.*;

import java.util.List;

public class SimpleJPQLTest {
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


    @Test
    public void 단일_메뉴명_조회_테스트() {
        String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode = 7";

        /*
        // 1) TypedQuery
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        String menuName = query.getSingleResult();
        System.out.println(menuName);
         */

        // 2) Query
        Query query = entityManager.createQuery(jpql);
        String menuName = (String) query.getSingleResult();
        System.out.println(menuName);
    }

    @Test
    public void 메뉴_단일행_조회_테스트() {
        String jpql = "SELECT m FROM menu1 m WHERE m.menuCode = 7";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu foundMenu = query.getSingleResult();
        System.out.println(foundMenu);
    }

    @Test
    public void 메뉴_다중행_조회_테스트() {
        String jpql = "SELECT m FROM menu1 m";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class); // 한 row에 대한 반환 타입작성
        List<Menu> menuList = query.getResultList(); //여러행조회
        menuList.forEach(menu -> {
            System.out.println(menu);
        }); //System.out::println 으로 축약가능
    }

    @Test
    public void DISTINCT_활용_테스트() {
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu1 m";
        List<Integer> categoryList = entityManager.createQuery(jpql, Integer.class).getResultList();
        categoryList.forEach(System.out::println);
    }

    @Test
    public void IN_활용_테스트() {
        String jpql = "SELECT m.categoryCode FROM menu1 m WHERE m.categoryCode IN(4,6)";
        List<Integer> categoryList = entityManager.createQuery(jpql, Integer.class).getResultList();
        categoryList.forEach(System.out::println);
    }

    @Test
    public void like_활용_테스트() {
        String jpql = "SELECT m FROM menu1 m WHERE m.menuName Like '%마늘%'";
        List<Menu> categoryList = entityManager.createQuery(jpql, Menu.class).getResultList();
        categoryList.forEach(System.out::println);
    }

    /*
    ## 파라미터 바인딩 ##
    1. 이름기준  ':' 뒤에 파라미터명 부여후 사용
    2. 위치기준  '?' 뒤에 위치 부여하여 샤용
     */

    @Test
    public void 이름_기준_파라미터_바인딩_테스트() {
        String jpql = "SELECT m FROM menu1 m WHERE m.menuCode = :code";

        Integer menuCode = 8;
        Menu foundMenu = entityManager.createQuery(jpql, Menu.class).setParameter("code", menuCode).getSingleResult();

        System.out.println(foundMenu);
    }

    @Test
    public void 위치_기준_파라미터_바인딩_테스트() {
        String jpql = "SELECT m FROM menu1 m WHERE m.categoryCode = ?1 AND m.menuPrice > ?2";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).setParameter(1,4).setParameter(2, 13000).getResultList();

        menuList.forEach(System.out::println);
    }

    @Test
    public void 리미트_활용_테스트(){ // 페이징처리등에서 사용됨
        int offset = 0;
        int display = 10;

        String jpql = "SELECT m FROM menu1 m ORDER BY m.menuPrice DESC";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset) // LIMIT 절의 offset 설정
                .setMaxResults(display)
                .getResultList();
        menuList.forEach(System.out::println);

    }

    /*
        ##그룹함수 (count, max, min, sum, avg) ##
        1. 그룹함수의 반환타입은 정수값일 경우 Long, 실수값일 경우 Double
        2. 값이 없는 상태에서 count의 결과는 0, count 제외 함수의 결과는 null
           => 반환 값을 담기 위한 변수 선언시 기본자료형으로 하게 되면 언박싱(Wrapper => primitive)tl NPE 발생 여지가 있음

        Count 특징
        1. 반환타입 Long
        2. 데이터가 없는 경우 0을 반환
        3. 최종 결과를 기록할 변수의 타입은 Long, long(primitive type)을 사용해서 상관없음
     */

    public void 통계함수_count외_테스트 (){
        String jpql = "SELECT SUM(m.menuPrice) FROM menu1 m WHERE m.categoryCode = ?1";
        Integer categoryCode = 10;
        Long totalPrice = entityManager.createQuery(jpql, Long.class)
                .setParameter(1, categoryCode)
                .getSingleResult();
        System.out.println(totalPrice);
    }

    @Test
    public void 통계함수_count_테스트(){
        String jpql = "SELECT COUNT(m.menuCode) FROM menu1 m WHERE m.categoryCode = ?1";

        Integer categoryCode = 10;

//        Long numOfRows = entityManager.createQuery(jpql, Long.class) //wrapper
//                .setParameter(1, categoryCode)
//                .getSingleResult();

        long numOfRows = entityManager.createQuery(jpql, Long.class) // 오토래핑됨
                .setParameter(1, categoryCode)
                .getSingleResult();
        System.out.println(numOfRows);
    }











}