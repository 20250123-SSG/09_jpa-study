package com.kyungbae.section03;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class EntityEnumMappingTest {


    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
    }

    @BeforeEach
    public void initEntityManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager(){
        entityManager.close();
    }

    @AfterAll
    public static void destroyEntityManagerFactory(){
        entityManagerFactory.close();
    }

    @Test
    public void enum_테스트(){
        System.out.println("UserRole.values: " + Arrays.toString(UserRole.values()));
        System.out.println("valueOf(ADMIN): " + UserRole.valueOf("ADMIN"));
        System.out.println("UserRole.USER: " + UserRole.USER);
        System.out.println("UserRole.USER.name(): " + UserRole.USER.name());
        System.out.println("UserRole.USER.ordinal(): " + UserRole.USER.ordinal());
    }

    @Test
    public void enum타입매핑테스트(){
        User user1 = User.builder()
                .userId("user01")
                .userPwd("1234")
                .nickname("관리자")
                .phone("010-2020-1313")
                .address("경기도 구리시")
                .email("mail@email.com")
                .userRole(UserRole.ADMIN)
                .enrollDate(LocalDateTime.now())
                .status("Y")
                .build();


        User user2 = User.builder()
                .userId("user02")
                .userPwd("4321")
                .nickname("사용자")
                .phone("010-3232-1212")
                .address("서울시 강동구")
                .email("mailmail@email.com")
                .userRole(UserRole.USER)
                .enrollDate(LocalDateTime.now())
                .status("Y")
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            entityManager.persist(user1);
            entityManager.persist(user2);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

}
