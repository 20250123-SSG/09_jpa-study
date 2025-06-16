package com.ino.section03.bidirection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class BidirectionAssociation {

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
    public void bidirection_relation_mapping_find(){

        int menuCode = 10;
        // get menu entity
        Menu menu = em.find(Menu.class, menuCode);

        Category cate = em.find(Category.class, 10);

        System.out.println(cate);
        cate.getMenuList().forEach(System.out::println);
    }

    @Test
    public void insertTest(){
        Menu menu = Menu.builder()
                .menuCode(40)
                .menuName("cowSoup")
                .menuPrice(123213123)
                .orderableStatus("Y")
                .category(em.find(Category.class, 4))
                .build();

        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(menu);
        et.commit();
    }

    @Test
    public void insertTest2(){
        Category cate = Category
                .builder()
                .categoryCode(18)
                .categoryName("newCate")
                .refCategoryCode(3)
                .build();

        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(cate);
        et.commit();
    }

}
