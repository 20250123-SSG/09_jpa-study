package com.younggalee.section03;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Arrays;

public class EntityEnumMappingTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() { entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test"); }

    @BeforeEach
    public void initEntityManager() { entityManager = entityManagerFactory.createEntityManager(); }

    @AfterEach
    public void destroyEntityManager() { entityManager.close(); }

    @AfterAll
    public static void destroyEntityManagerFactory() { entityManagerFactory.close(); }

    @Test
    public void testEnum() {
        System.out.println(Arrays.toString(UserRole.values()));
        System.out.println(UserRole.valueOf("ADMIN"));
        System.out.println(UserRole.USER);
        System.out.println(UserRole.USER.name());
        System.out.println(UserRole.USER.ordinal());
    }

    @Test
    public void enum타입매핑테스트() {
        User user = User.builder()
                .userId("admin")
                .userPwd("1324")
                .nickName("관리자")
                .phone("010-1111-2222")
                .enrollDate(LocalDateTime.now())
                .userRole(UserRole.ADMIN)
                .build();

        User user2 = User.builder()
                .userId("user")
                .userPwd("1324")
                .nickName("사용자")
                .phone("010-1111-2222")
                .enrollDate(LocalDateTime.now())
                .userRole(UserRole.USER)
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            entityManager.persist(user);
            entityManager.persist(user2);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
} // 상수에 대한 순번으로 저장됨 : ADMIN, USER 가 아니라 0, 1 (enum default mapping 시)
