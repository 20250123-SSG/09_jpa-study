package com.kyungbae.section02.one_to_many;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class OneToManyAssociationTest {

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

    /*
        카테고리 기능
        - 카테고리 정보 조회 (+ 해당 카테고리의 메뉴들 조회)
        - 카테고리 등록
     */

    @Test
    public void 일대다연관관계_객체그래프탐색을이용한조회테스트(){
        int categoryCode = 4;

        Category foundedCategory = entityManager.find(Category.class, categoryCode);

        System.out.println(foundedCategory.getCategoryCode());
        System.out.println(foundedCategory.getCategoryName());

        System.out.println(foundedCategory.getMenuList());
    }

    @Test
    public void 일대다연관관계_객체삽입테스트1(){
        Category categoryToRegist = Category.builder()
                .categoryCode(14)
                .categoryName("분식")
                .refCategoryCode(null)
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(categoryToRegist);
        transaction.commit();
    }

    @Test
    public void 일대다연관관계_객체삽입테스트2(){
        Menu menu1 = Menu.builder()
                .menuCode(30)
                .menuName("엄청매운떡볶이")
                .menuPrice(10000)
                .orderableStatus("Y")
                .build();

        Menu menu2 = Menu.builder()
                .menuCode(31)
                .menuName("엄청순한떡볶이")
                .menuPrice(12000)
                .orderableStatus("Y")
                .build();

        List<Menu> menuList = List.of(menu1, menu2);

        Category categoryToRegist = Category.builder()
                .categoryCode(16)
                .categoryName("신규")
                .refCategoryCode(null)
                .menuList(menuList)
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

//        entityManager.persist(menu1);
//        entityManager.persist(menu2);
        entityManager.persist(categoryToRegist);
        transaction.commit();
    }
}
