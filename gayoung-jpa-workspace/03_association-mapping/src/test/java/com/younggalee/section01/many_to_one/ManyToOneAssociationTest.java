package com.younggalee.section01.many_to_one;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ManyToOneAssociationTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() { entityManagerFactory = Persistence.createEntityManagerFactory("JPA_Test"); }

    @BeforeEach
    public void initEntityManager() { entityManager = entityManagerFactory.createEntityManager(); }

    @AfterEach
    public void destroyEntityManager() { entityManager.close(); }

    @AfterAll
    public static void closeEntityManagerFactory() { entityManagerFactory.close(); }

    /*
        메뉴기능
        - 메뉴 정보 조회 ( + 해당 메뉴의 카테고리 상세 정보 )
        - 메뉴 등록     ( + 참조 카테고리가 신규일 경우 카테고리 같이 등록)

     */

    @Test
    public void 다대일_연관관계_객체그래프탐색을_이용한_조회_테스트() {
        // find로 조회하면 그래프처럼 연관관계의 내용까지 조회되는걸 확인하는 테스트

        int menuCode = 15;

        // 메뉴조회
        Menu foundedMenu = entityManager.find(Menu.class, menuCode);  // 6번 카테고리 엔티티에 대한 정보도 함께 담겨서 조회할 수 있음.
        System.out.println(foundedMenu);

        // 조회시 조인 구문이 실행되면서 연관 테이블을 함께 조회해옴
    }

    @Test
    public void 다대일_연관관계_객체지향쿼리JPQL를_이용한_조회_테스트(){
        int menuCode = 15;
        // join문이 조금 다름. 어떤 연관관계의 데이터를 사용할건지
        String jpql = "SELECT c.categoryName FROM menu1 m JOIN m.category c WHERE m.menuCode = " + menuCode;
        // jpql의 경우, find 아님
        String foundedCategoryName = entityManager.createQuery(jpql, String.class).getSingleResult(); // 해당구문을 실행했을 때, String 타입으로 반환받겠다
        System.out.println(foundedCategoryName);
    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트1() {
        // 메뉴하나를 인서트
        Menu menuToRegist = Menu.builder()
                .menuName("빙수")
                .menuPrice(10000)
                .orderableStatus("Y")
                .category(Category.builder().categoryCode(4).build())
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        entityManager.getTransaction().begin();

        entityManager.persist(menuToRegist);
        transaction.commit();



    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트2() { // 없는 카테고리로 인서트하기
        // 메뉴하나를 인서트
        Menu menuToRegist = Menu.builder()
                .menuName("빙수")
                .menuPrice(10000)
                .orderableStatus("Y")
                .category(Category.builder()
                        .categoryCode(13)
                        .categoryName("새로운카테고리")
                        .refCategoryCode(3)
                        .build())
                .build();

        // menu 엔티티에 대해 영속화(insert)시키면 안됨. 부모엔티티(카테고리)가 먼저 영속화 되어있어야함.
        // 부모먼저 DB에 저장시키고 하위객체 저장해야됨. (연관관계)
        // 엔티티 클래스에 영속성전이설정(cascade persist)해줘야함.
        EntityTransaction transaction = entityManager.getTransaction();
        entityManager.getTransaction().begin();

        entityManager.persist(menuToRegist);
        transaction.commit();



    }


}













