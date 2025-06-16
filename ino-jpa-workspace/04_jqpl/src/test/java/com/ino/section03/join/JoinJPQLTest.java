package com.ino.section03.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class JoinJPQLTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public static void initEntityManagerFactory(){ emf = Persistence.createEntityManagerFactory("jpa_test"); }

    @BeforeEach
    public void initEntityManager(){ em = emf.createEntityManager(); }

    @AfterEach
    public void destroyEntityManager(){ em.close(); }

    @AfterAll
    public static void destroyEntityManagerFactory() { emf.close(); }

    /*
        일반 조인 : 일반적인 SQL 조인
        페치 조인 : JPQL에서 성능 최적화를 위해 제공하는 기능
     */


    @Test
    public void inner_join_test(){
//        String jpql = "SELECT m, c FROM menu3 m JOIN category3 c ON m.categoryCode = c.categoryCode "; // 연관관계 매핑x시

        String jpql = "SELECT m FROM menu3 m JOIN m.category";

        List<Menu> menuList = em.createQuery(jpql, Menu.class).getResultList();

        menuList.forEach(System.out::println);

    }

    @Test
    public void outer_join_test(){
        String jpql = "SELECT m.menuName, c.categoryName from menu3 m RIGHT OUTER JOIN m.category c";

        List<Object[]> menuList = em.createQuery(jpql, Object[].class).getResultList();

        menuList.forEach( row -> {
            System.out.println(Arrays.toString(row));
        });

    }

    @Test
    public void theta_join_test(){
         String jpql = "SELECT m.menuName, c.categoryName FROM menu3 m, category3 c";
         List<Object[]> menuList = em.createQuery(jpql, Object[].class).getResultList();

         menuList.forEach(row -> {
             System.out.println(Arrays.toString(row));
         });
    }

    @Test
    public void fetch_join_test(){

        String jpql = "SELECT m FROM menu3 m JOIN FETCH m.category";

        List<Menu> menuList = em.createQuery(jpql, Menu.class).getResultList();

        menuList.forEach(System.out::println);

        //내부 조인으로 실행시 N+1 문제 발생

    }



}
