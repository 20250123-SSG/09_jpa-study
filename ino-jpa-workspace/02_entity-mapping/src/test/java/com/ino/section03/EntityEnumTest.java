package com.ino.section03;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

public class EntityEnumTest {
    private static EntityManagerFactory entityManagerFactory; // Em 생성을 위한 인스턴스, 싱글톤 관리 권장
    private EntityManager entityManager; // 엔티티 관리 인스턴스, 엔티티 CRUD와 관련된 task 진행

    @BeforeAll //  테스트 클래스의 인스턴스가 생성되기 전에 해당 메서드가 실행되기 때문에 static 지정 필
    public static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test"); //   <persistence-unit name="jpa_test">로 정의한 이름 설정
    }

    @BeforeEach
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager() {
        entityManager.close();
        ;
    }

    @AfterAll
    public static void destroyEntityManagerFactory() {
        entityManagerFactory.close();
    }


    @Test
    public void test_enum(){
        User user1 = User.builder()
                .userNo(1)
                .nickname("뽀로로")
                .userId("pororo")
                .userPwd("pororo123!")
                .phone("010-1111-2222")
                .email("pororo@example.com")
                .address("뽀롱뽀롱 마을")
                .enrollDate(LocalDateTime.of(2023, 5, 10, 14, 30, 0))
                .userRole(UserRole.USER)
                .status("ACTIVE")
                .build();

        // 두 번째 더미 User 데이터
        User user2 = User.builder()
                .userNo(2)
                .nickname("루피")
                .userId("loopy")
                .userPwd("loopy456#")
                .phone("010-3333-4444") // phone과 enroll_date는 unique constraints이므로, 다른 값을 줘야 합니다.
                .email("loopy@example.com")
                .address("숲 속의 집")
                .enrollDate(LocalDateTime.of(2024, 1, 15, 9, 0, 0))
                .userRole(UserRole.ADMIN) // 다른 역할
                .status("ACTIVE")
                .build();
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        try{
            entityManager.persist(user1);
            entityManager.persist(user2);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

}
