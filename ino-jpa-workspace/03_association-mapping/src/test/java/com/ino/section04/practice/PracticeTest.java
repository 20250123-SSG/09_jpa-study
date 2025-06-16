package com.ino.section04.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PracticeTest {

    // 1 team - N member
    // 1 member - 1 locker

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void initEntityManagerFactory(){ emf = Persistence.createEntityManagerFactory("jpa-practice"); }

    @BeforeEach
    public void initEntityManager(){ em = emf.createEntityManager(); }

    @AfterEach
    public void destroyEntityManager(){ em.close(); }

    @AfterAll
    public static void destroyEntityManagerFactory() { emf.close(); }

    @Test
    public void testPractice(){
    }
}
