package com.kyungbae.section02.projection;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class ProjectionJPQLTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
    }

    @BeforeEach
    public void initEntityManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager(){
        entityManager.close();
    }

    @AfterAll
    public static void destroyEntityManagerFactory(){
        entityManagerFactory.close();
    }

    @Test
    public void 엔티티프로젝션_테스트(){
        String jpql = "SELECT m FROM menu2 m WHERE m.menuCode = 3";

        Menu foundMenu = entityManager.createQuery(jpql, Menu.class).getSingleResult();

        System.out.println(foundMenu);
        System.out.println(entityManager.contains(foundMenu));

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        foundMenu.setOrderableStatus("N");
        transaction.commit();
    }

    @Test
    public void 임베디드프로젝션_테스트(){
        String jpql = "SELECT m.menuInfo FROM menu2 m WHERE m.menuCode = 5";

        MenuInfo menuInfo = entityManager.createQuery(jpql, MenuInfo.class).getSingleResult();

        System.out.println(menuInfo);
    }

    @Test
    public void 스칼라프로젝션_테스트1(){
        String jpql = "SELECT c.categoryName FROM category2 c";

        List<String> categoryNames = entityManager.createQuery(jpql, String.class).getResultList();

        categoryNames.forEach(System.out::println);
    }

    @Test
    public void 스칼라프로젝션_테스트2(){
        String jpql = "SELECT c.categoryCode, c.categoryName FROM category2 c"; // 다중열로 조회시 타입을 지정할 수 없음

        List<Object[]> categoryNames = entityManager.createQuery(jpql).getResultList();

        categoryNames.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void newDTO프로젝션_테스트(){
        String jpql = """
                    SELECT
                        new com.kyungbae.section02.projection.CategoryResponseDto
                            (c.categoryCode, c.categoryName)
                    FROM
                        category2 c
                    """;

        List<CategoryResponseDto> list = entityManager.createQuery(jpql, CategoryResponseDto.class).getResultList();

        list.forEach(System.out::println);
    }

}
