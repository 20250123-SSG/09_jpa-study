package com.younggalee.section02.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProjectionJPQLTest {
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
    public void testProjectionJPQL() {
        String jpqlQuery = "SELECT m FROM menu2 m WHERE m.menuCode = 3";

        Menu foundMenu = entityManager.createQuery(jpqlQuery, Menu.class).getSingleResult();

        System.out.println(foundMenu);
        System.out.println(entityManager.contains(foundMenu));

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        foundMenu.setOrderableStatus("Y");
        transaction.commit();
    }

    @Test
    public void 임베디드_프로젝션_테스트() { // 메뉴 정보의 일부 데이터를 외부에서 다루고 싶을때
        String jpql = "SELECT m.menuInfo FROM menu2 m WHERE m.menuCode = 5";
        MenuInfoEmbeded menuInfo = entityManager.createQuery(jpql, MenuInfoEmbeded.class).getSingleResult();
        System.out.println(menuInfo);
    }

    @Test
    public void 스칼라_프로젝션_테스트1(){
        String jpql = "SELECT c.categoryName FROM category2 c"; // 단일열 > 반환타입 지정가능
        List<String> categoryNames = entityManager.createQuery(jpql, String.class).getResultList();

        categoryNames.forEach(System.out::println);
    }

    @Test // 반환하고자하는 데이터가 두개 이상이라면..? 반환타입지정불가능.. 그냥 쿼리로 받아야함
    public void 스칼라_프로젝션_테스트2(){
        String jpql = "SELECT c.categoryName, c.categoryCode FROM category2 c";
         List<Object[]> categoryNames = entityManager.createQuery(jpql).getResultList();
        categoryNames.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });

    }

    @Test
    public void new_DTO_프로젝션_테스트(){ // 해당 객체로 조회된 내용을 받을 수 있다.
        String jpql = "SELECT new com.younggalee.section02.projection.CategoryResponseDto(c.categoryCode, c.categoryName) FROM category2 c ";
        List<CategoryResponseDto> categoryList = entityManager.createQuery(jpql, CategoryResponseDto.class).getResultList();
        categoryList.forEach(System.out::println);
    }


}
