package com.jjanggu.section02.one_to_many;

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
    public static void initEntityManagerFactory(){entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");}
    @BeforeEach
    public void initEntityManager(){entityManager = entityManagerFactory.createEntityManager();}
    @AfterEach
    public void destroyEntityManager(){entityManager.close();}
    @AfterAll
    public static void destroyEntityManagerFactory(){ entityManagerFactory.close(); }

    /*
        카테고리 기능
        - 카테고리 정보 조회 (+ 해당 카테고리의 메뉴들 조회)
        - 카테고리 등록
     */
    @Test
    public void 일대다_연관관계_객체그래프탐색을_이용한_조회_테스트(){
        int categoryCode = 4;

        Category foundedCategory = entityManager.find(Category.class, categoryCode);

        System.out.println(foundedCategory.getCategoryName());
        System.out.println(foundedCategory.getRefCategoryCode());

        System.out.println(foundedCategory.getMenuList());
        /*
            @ManyToOne 는 fetch 기본값이 EAGER (즉시로딩)
            @OneToMany 는 fetch 기본값이 LAZY (지연로딩)
         */
    }

    @Test
    public void 일대다_연관관계_객체_삽입_테스트1(){
        Category categoryToRegist = Category
                .builder()
                .categoryCode(15)
                .categoryName("테스트카테1")
                .refCategoryCode(null)
                .build();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(categoryToRegist);
        entityTransaction.commit();
    }

    @Test
    public void 일대다_연관관계_객체_삽입_테스트2(){
        Menu menu1 = Menu.builder()
                .menuCode(30)
                .menuName("테스트메뉴2_1")
                .menuPrice(10000)
                .orderableStatus("Y")
                .build();
        Menu menu2 = Menu.builder()
                .menuCode(31)
                .menuName("테스트메뉴2_2")
                .menuPrice(20000)
                .orderableStatus("Y")
                .build();
        List<Menu> menuListToRegist = List.of(menu1, menu2);

        Category categoryToRegist = Category.builder()
                .categoryCode(16)
                .categoryName("테스트카테2")
                .refCategoryCode(null)
                .menuList(menuListToRegist)
                .build();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(categoryToRegist);
        entityTransaction.commit();
    }


}
