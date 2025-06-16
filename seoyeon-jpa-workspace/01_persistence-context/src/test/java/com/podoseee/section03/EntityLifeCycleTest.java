package com.podoseee.section03;

import com.podoseee.entity.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityLifeCycleTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
    }

    @BeforeEach
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @AfterAll
    public static void destroyEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void 비영속_테스트(){

        // 새로 생성한 엔티티 객체 => 영속성 컨텍스트에 저장되지 않음
        // 즉, DB와 관련 없는 비영속 상태
        Menu newMenu = Menu.builder()
                .menuCode(3)
                .menuName("생갈치 쉐이크")
                .menuPrice(6000)
                .categoryCode(10)
                .orderableStatus("Y")
                .build();

        // find() => 조회된 엔티티가 영속 상태 (영속성 컨텍스트에 저장)
        Menu foundedMenu = entityManager.find(Menu.class, 3);

        System.out.println("newMenu: " + newMenu);
        System.out.println("foundedMenu: " + foundedMenu);
        System.out.println(newMenu == foundedMenu);

    }

    @Test
    public void 영속_find_테스트(){

        int menuCode = 3; // 조회할 메뉴 식별자

        /*
            ## find() 기본 동작 원리 ##
            1. 식별자를 통해 기본적으로 영속성 컨텍스트의 1차 캐시로부터 먼저 조회함 (조회결과가 있으면 해당 엔티티 반환)
            2. 영속성 컨텍스트로부터 찾지 못하면 DB로부터 Select 쿼리가 실행되면서 조회
            3. 조회된 엔티티를 영속성 컨텍스트에 저장시킴
         */



    }


}
