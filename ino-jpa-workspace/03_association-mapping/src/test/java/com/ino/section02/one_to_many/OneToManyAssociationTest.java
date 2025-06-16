package com.ino.section02.one_to_many;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class OneToManyAssociationTest {
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
    public void OneToManyGraphTest(){
        int cCode = 4;

        Category foundedCate = em.find(Category.class, cCode);

        System.out.println(foundedCate.getCategoryName());
        System.out.println(foundedCate.getRefCategoryCode());

        System.out.println(foundedCate.getMenuList());
    }

    @Test
    public void OneToManyObjectInsertTest1(){
        Category cate1 = Category
                .builder()
                .categoryCode(15)
                .categoryName("testCate1")
                .refCategoryCode(null)
                .build();

        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(cate1);
        et.commit();
    }

    @Test
    public void OneToManyObjectInsertTest2(){
        Category cate1 = Category
                .builder()
                .categoryCode(18)
                .categoryName("testCate1")
                .refCategoryCode(null)
                .build();

        Menu menu1 = Menu.builder()
                .menuCode(33)
                .menuName("testMenu2_1")
                .menuPrice(10000)
                .orderableStatus("Y")
                .build();

        Menu menu2 = Menu.builder()
                .menuCode(34)
                .menuName("testMenu2_2")
                .menuPrice(20000)
                .orderableStatus("Y")
                .build();

        List<Menu> list = List.of(menu1, menu2);

        Category cate2 = Category.builder()
                .categoryCode(22)
                .categoryName("testCate2")
                .refCategoryCode(null)
                .menuList(list)
                .build();

        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(cate2);

        et.commit();
    }
}
