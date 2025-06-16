package com.younggalee.section02.one_to_many;

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
    //JUnit 5에서 @BeforeAll은 기본적으로 static 메서드여야 함. 테스트인스턴스 없이 실행되야해서 (main에서그런것처럼)
    public static void initEntityManagerFactory() { entityManagerFactory = Persistence.createEntityManagerFactory("JPA_Test"); }

    @BeforeEach
    public void initEntityManager() { entityManager = entityManagerFactory.createEntityManager(); }

    @AfterEach
    public void destroyEntityManager() { entityManager.close(); }

    @AfterAll
    public static void closeEntityManagerFactory() { entityManagerFactory.close(); }


    /*
        카테고리 기능
         - 카테고리 정보 조회 (+해당 카테고리의 메뉴들 조회)
         - 카테고리 등록
     */

    @Test
    public void 일대다_연관관계_객체그래프탐색을_이용한_조회_테스트() {
        int categoryCode = 4;

        Category foundedCategory = entityManager.find(Category.class, categoryCode);

        System.out.println(foundedCategory.getCategoryName());
        System.out.println(foundedCategory.getRefCategoryCode());

        System.out.println(foundedCategory.getMenuList());

        /*
        @ManyToOne은 fetch 기본값이 EAGER (즉시로딩)
        @OneToMany은 fetch 기본값이 LAZY  (지연로딩)
         */

        // One to Many 는 default가 지연로딩(LAZY)    > fetch EAGER로 설정 바꾸면 즉시로딩 가능함

        /*
        Hibernate:
            select
                c1_0.category_code,
                c1_0.category_name,
                c1_0.ref_category_code
            from
                tbl_category c1_0
            where
                c1_0.category_code=?
        한식
        1
        Hibernate:
            select
                ml1_0.category_code,
                ml1_0.menu_code,
                ml1_0.menu_name,
                ml1_0.menu_price,
                ml1_0.orderable_status
            from
                tbl_menu ml1_0
            where
                ml1_0.category_code=?
        [Menu(menuCode=5, menuName=앙버터김치찜, menuPrice=13000, categoryCode=4, orderableStatus=N), Menu(menuCode=6, menuName=생마늘샐러드, menuPrice=12000, categoryCode=4, orderableStatus=Y), Menu(menuCode=7, menuName=민트미역국, menuPrice=15000, categoryCode=4, orderableStatus=Y), Menu(menuCode=8, menuName=한우딸기국밥, menuPrice=20000, categoryCode=4, orderableStatus=Y), Menu(menuCode=23, menuName=아메리카노, menuPrice=2000, categoryCode=4, orderableStatus=Y), Menu(menuCode=24, menuName=빙수, menuPrice=10000, categoryCode=4, orderableStatus=Y)]

                 */


    }

    @Test
    public void 일대다_연관관계_객체_삽입_테스트1(){
        Category categoryToRegist = Category
                .builder()
                .categoryCode(15)
                .categoryName("테스트카테고리")
                .refCategoryCode(null)
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(categoryToRegist);
        transaction.commit();
    }

    @Test
    public void 일대다_연관관계_객체_삽입_테스트2(){
        Menu menu1 = Menu.builder()
                .menuCode(30)
                .menuName("테스트메뉴1")
                .menuPrice(10000)
                .orderableStatus("Y")
                .build();

        Menu menu2 = Menu.builder()
                .menuCode(301)
                .menuName("테스트메뉴2")
                .menuPrice(20000)
                .orderableStatus("Y")
                .build();

        List<Menu> menuListToRegist = List.of(menu1, menu2);

        Category categoryToRegist = Category
                .builder()
                .categoryCode(16)
                .categoryName("테스트카테고리2")
                .refCategoryCode(null)
                .menuList(menuListToRegist) // 영속성 전이설정해놔서 가능
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(categoryToRegist);
        transaction.commit();
    }
}
