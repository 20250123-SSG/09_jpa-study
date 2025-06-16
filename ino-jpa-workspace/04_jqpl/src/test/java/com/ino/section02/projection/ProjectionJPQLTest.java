package com.ino.section02.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class ProjectionJPQLTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void initEntityManagerFactory(){ emf = Persistence.createEntityManagerFactory("jpa_test"); }

    @BeforeEach
    public void initEntityManager(){ em = emf.createEntityManager(); }

    @AfterEach
    public void destroyEntityManager(){ em.close(); }

    @AfterAll
    public static void destroyEntityManagerFactory() { emf.close(); }

    @Test
    public void entity_projection_test(){
        String jpql = "SELECT m FROM menu2 m WHERE m.menuCode = 3";
        Menu menu = em.createQuery(jpql, Menu.class).getSingleResult();

        System.out.println(menu);

        // 영속 상태 확인
        System.out.println(em.contains(menu));

        EntityTransaction et = em.getTransaction();
        et.begin();
        menu.setOrderableStatus("Y");
        et.commit(); // dirtychecking -> update

    }

    @Test
    public void embedded_projection_test(){
        String jpql = "SELECT m.menuInfo FROM menu2 m WHERE m.menuCode = 5";
        MenuInfo menuInfo = em.createQuery(jpql, MenuInfo.class).getSingleResult();
        System.out.println(menuInfo);
    }

    @Test
    public void scala_projection_test1(){
        String jpql = "SELECT c.categoryName FROM category2 c";

        List<String> categoryNames = em.createQuery(jpql, String.class).getResultList();

        categoryNames.forEach(System.out::println);
    }

    @Test
    public void scala_projection_test2(){
        String jpql = "SELECT c.categoryCode, c.categoryName FROM category2 c";

        List<Object[]> list = em.createQuery(jpql).getResultList(); // 1 column -> ? = Object 2 or more columns -> ? = Object[]

        list.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void new_DTO_projection_test(){
        String jpql = "SELECT new com.ino.section02.projection.CategoryResponseDto(c.categoryCode, c.categoryName) FROM category2 c";
        List<CategoryResponseDto> list = em.createQuery(jpql, CategoryResponseDto.class).getResultList();
        list.forEach(System.out::println);
    }
}
