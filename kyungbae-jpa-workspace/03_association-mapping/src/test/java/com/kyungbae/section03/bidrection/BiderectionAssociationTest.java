package com.kyungbae.section03.bidrection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class BiderectionAssociationTest {

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
    public void 양방향연관관계매핑_조회테스트(){
        // 메뉴 엔티티 조회
        Menu foundedMenu = entityManager.find(Menu.class, 10);
        System.out.println(foundedMenu);

        /*
            ## 유의사항
            toString 메소드 오버라이딩시 연관관계로 매핑되어있는 엔티티 활용시
            재귀호출이 일어남 => StackOverFlowError 발생
         */

        // 카테고리 엔티티 조회
        Category foundedCategory = entityManager.find(Category.class, 10);
        System.out.println(foundedCategory);
        foundedCategory.getMenuList().forEach(System.out::println);
    }

    @Test
    public void 삽입테스트1(){
        Menu menuToRegist = Menu.builder()
                .menuCode(33)
                .menuName("김치볶음")
                .menuPrice(12000)
                .orderableStatus("Y")
                .category(entityManager.find(Category.class, 4))
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(menuToRegist);
        transaction.commit();
    }

    @Test
    public void 삽입테스트2(){
        Category categoryToRegist = Category.builder()
                .categoryCode(18)
                .categoryName("카테고리고리")
                .refCategoryCode(3)
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(categoryToRegist);
        transaction.commit();
    }
}
