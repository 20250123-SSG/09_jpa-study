package com.kyungbae.section04.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PracticeTest {
    /*
        예제.

     */


    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_practice");
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
    public void 삽입테스트(){
        Member member1 = Member.builder()
                .memberName("길동")
                .team(Team.builder().teamName("1팀").build())
                .locker(Locker.builder().lockerName("라카").build())
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member1);
        transaction.commit();
    }

}
