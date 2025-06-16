package com.podoseee.section01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityManagerLifeCycleTest {

    private static EntityManagerFactory entityManagerFactory; // EntityManagerFactory는 static으로 선언 (프로젝트 단위로 한 번만 생성)
    private EntityManager entityManager; // EntityManager는 테스트마다 새로 생성

    @BeforeAll
    public static void initEntityManagerFactory() {
        // EntityManagerFactory (싱글톤 관리) 생성
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
    }

    @BeforeEach
    public void initEntityManager() {
        // EntityManagerFactory를 통해 EntityManager를 생성
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void 요청1() {
        System.out.println("테스트 메소드 1");
        System.out.println("EntityManagerFactory.hashCode: " + entityManagerFactory.hashCode());
        System.out.println("EntityManager.hashCode: " + entityManager.hashCode());
    }

    @Test
    public void 요청2() {
        System.out.println("테스트 메소드 2");
        System.out.println("EntityManagerFactory.hashCode: " + entityManagerFactory.hashCode());
        System.out.println("EntityManager.hashCode: " + entityManager.hashCode());
    }

    @AfterEach
    public void destroyEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close(); // EntityManager 종료
        }
    }

    @AfterAll
    public static void destroyEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

}
