package com.ino.section01.many_to_one;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ManyToOneAssociationTest {

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
    /*
        menu logic
        - find menu ( + menu's category detail)
        - regist menu ( + if category = new, regist category together )

     */

    @Test
    public void 다대일_연관관계_객체그래프탐색을_이용한_조회_테스트(){

        int menuCode = 15;

        // get menu
        Menu menu = em.find(Menu.class, menuCode);

        System.out.println(menu);

    }

    @Test
    public void 다대일_연관관계_객체지향_쿼리_이용_조회_테스트(){
        int menuCode = 15;

        String jpql = "SELECT c.categoryName FROM menu1 m JOIN m.category c WHERE m.menuCode = " + menuCode; // real table name x -> use JPA's entity name, entity's field name

        String foundedCategoryName = em.createQuery(jpql, String.class).getSingleResult();
        System.out.println(foundedCategoryName);
    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트1(){
        Menu menu1 = Menu.builder()
                .menuName("스테이크 정식")
                .menuPrice(25000)
                .orderableStatus("Y")
                .category(Category.builder().categoryCode(4).build())
                .build();

        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(menu1);
        et.commit();
    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트2(){
        Category category1 = Category.builder()
                .categoryCode(16)
                .categoryName("helloworld")
                .refCategoryCode(3)
                .build();
        Menu menu1 = Menu.builder()
                .menuName("함바그 정식")
                .menuPrice(25000)
                .orderableStatus("Y")
                .category(category1)
                .build();


        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(menu1);
        et.commit();
    }
}

