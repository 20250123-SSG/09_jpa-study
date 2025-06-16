package com.jjanggu.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Date;

public class EntityColumnMappingTest {

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

    @Test
    public void 컬럼_매핑_테스트(){
        User user = User.builder()
                //.userNo(1)
                .userId("admin01")
                .userPwd("1234")
                .nickname("관리자")
                .phone("010-1234-5678")
                .email("admin@gmail.com")
                .address("서울시 강서구")
                //.enrollDate(new Date())
                .enrollDate(LocalDateTime.now())
                .userRole("ADMIN")
                .status("Y")
                .build();

        User user2 = User.builder()
                //.userNo(1)
                .userId("user01")
                .userPwd("pass01")
                .nickname("사용자")
                .phone("010-9999-1111")
                .email("user@gmail.com")
                .address("부산시 강서구")
                //.enrollDate(new Date())
                .enrollDate(LocalDateTime.now())
                .userRole("USER")
                .status("Y")
                .build();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            entityManager.persist(user);
            entityManager.persist(user2);
            entityTransaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            entityTransaction.rollback();
        }

    }
}
