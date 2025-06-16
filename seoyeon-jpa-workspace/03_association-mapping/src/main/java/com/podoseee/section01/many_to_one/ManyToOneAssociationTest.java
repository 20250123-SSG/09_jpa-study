package com.podoseee.section01.many_to_one;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ManyToOneAssociationTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() { entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test"); }
    @BeforeEach
    public void initEntityManager(){ entityManager = entityManagerFactory.createEntityManager(); }
    @AfterEach
    public void destroyEntityManager(){ entityManager.close(); }
    @AfterAll
    public static void destroyEntityManagerFactory() { entityManagerFactory.close(); }

    /*
        메뉴기능
        - 메뉴 정보 조회 (+ 해당 메뉴의 카테고리 상세 정보)
        - 메뉴 등록      (+ 참조 카테고리가 신규일 경우 카테고리 같이 등록)
     */


    @Test
    public void 다대일_연관관계_객체그래프탐색을_이용한_조회_테스트(){

        int menuCode = 15;

        // 메뉴 조회
        Menu foundedMenu = entityManager.find(Menu.class, menuCode);
        // 조회시 조인 구문이 실행되면서 연관 테이블을 함께 조회해옴

        //System.out.println(foundedMenu);

        System.out.println(foundedMenu.getMenuName());
        System.out.println(foundedMenu.getMenuPrice());
        //System.out.println(foundedMenu.getCategory());

    }

    @Test
    public void 다대일_연관관계_객체지향쿼리를_이용한_조회_테스트(){
        int menuCode = 15;

        String jpql = "SELECT c.categoryName FROM menu1 m JOIN m.category c WHERE m.menuCode = " + menuCode;
        String foundedCategoryName = entityManager.createQuery(jpql, String.class).getSingleResult();

        System.out.println(foundedCategoryName);

    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트1(){
        Menu menuToRegist = Menu.builder()
                .menuName("빙수")
                .menuPrice(10000)
                .orderableStatus("Y")
                .category(Category.builder().categoryCode(4).build())
                .build();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(menuToRegist);
        entityTransaction.commit();
    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트2(){
        Menu menuToRegist = Menu.builder()
                .menuName("요거트")
                .menuPrice(7000)
                .orderableStatus("Y")
                .category(Category.builder()
                        .categoryCode(13)
                        .categoryName("요거")
                        .refCategoryCode(3)
                        .build())
                .build();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(menuToRegist);
        entityTransaction.commit();


    }






}