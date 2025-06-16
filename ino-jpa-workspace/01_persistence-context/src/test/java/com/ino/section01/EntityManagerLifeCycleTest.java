package com.ino.section01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityManagerLifeCycleTest {

    private static EntityManagerFactory entityManagerFactory; // Em 생성을 위한 인스턴스, 싱글톤 관리 권장
    private EntityManager entityManager; // 엔티티 관리 인스턴스, 엔티티 CRUD와 관련된 task 진행

    @BeforeAll //  테스트 클래스의 인스턴스가 생성되기 전에 해당 메서드가 실행되기 때문에 static 지정 필
    public static void initEntityManagerFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test"); //   <persistence-unit name="jpa_test">로 정의한 이름 설정
    }

    @BeforeEach
    public void initEntityManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager(){
        entityManager.close();;
    }

    @AfterAll
    public static void destroyEntityManagerFactory(){
        entityManagerFactory.close();
    }

    @Test
    public void req1(){
        System.out.println("test method 1");
        System.out.println("EntityManagerFactory.hashCode: " + entityManagerFactory.hashCode());
        System.out.println("EntityManager.hashCode: " + entityManager.hashCode());
    }

    @Test
    public void req2(){
        System.out.println("test method 2");
        System.out.println("EntityManagerFactory.hashCode: " + entityManagerFactory.hashCode());
        System.out.println("EntityManager.hashCode: " + entityManager.hashCode());
    }

    @Test
    public void req3(){
        System.out.println("test method 3");
        System.out.println("EntityManagerFactory.hashCode: " + entityManagerFactory.hashCode());
        System.out.println("EntityManager.hashCode: " + entityManager.hashCode());
    }
}
