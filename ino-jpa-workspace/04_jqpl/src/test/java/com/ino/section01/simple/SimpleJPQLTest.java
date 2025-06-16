package com.ino.section01.simple;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

public class SimpleJPQLTest {
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

    @Test
    public void single_menu_name_find_test(){
        String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode = 7";

        // String Class로 리턴받는 쿼리
        TypedQuery<String> query = em.createQuery(jpql, String.class);

        // Object Class로 리턴받는 쿼리
        Query query1 = em.createQuery(jpql);

        System.out.println(query.getSingleResult());
        System.out.println(query1.getSingleResult());
    }

    @Test
    public void menu_singleresult_find_test(){
        String jpql = "SELECT m FROM menu1 m WHERE m.menuCode = 7";

        TypedQuery<Menu> query = em.createQuery(jpql, Menu.class);

        System.out.println(query.getSingleResult());
    }

    @Test
    public void menu_multipleresult_find_test(){
        String jpql = "SELECT m FROM menu1 m";

        TypedQuery<Menu> query = em.createQuery(jpql, Menu.class);

        List<Menu> list = query.getResultList();

        list.forEach(System.out::println);
    }

    @Test
    public void DISTINCT_test(){
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu1 m";

        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);

        List<Integer> list = query.getResultList();

        list.forEach(System.out::println);
    }

    @Test
    public void IN_test(){
        String jpql = "SELECT m FROM menu1 m WHERE m.categoryCode IN(4, 6)";
        TypedQuery<Menu> query = em.createQuery(jpql, Menu.class);
        List<Menu> list = query.getResultList();

        list.forEach(System.out::println);
    }

    @Test
    public void LIKE_test(){
        String jpql = "SELECT m from menu1 m where m.menuName LIKE '%마늘%'";

        TypedQuery<Menu> query = em.createQuery(jpql, Menu.class);
        List<Menu> list = query.getResultList();

        list.forEach(System.out::println);
    }

    @Test
    public void name_parameter_binding(){
        String jpql = "SELECT m from menu1 m where m.menuCode = :code";

        Integer menuCode = 8;
        TypedQuery<Menu> query = em.createQuery(jpql, Menu.class);
        query.setParameter("code", menuCode);
        Menu menu = query.getSingleResult();

        System.out.println(menu);
    }

    @Test
    public void position_parameter_binding(){
        String jpql = "SELECT m from menu1 m where m.menuCode = ?2 and m.menuPrice = ?1";

        Integer menuCode = 8;
        Menu menu = em.createQuery(jpql, Menu.class)
                .setParameter(2, menuCode)
                .setParameter(1, 20000)
                .getSingleResult();

        System.out.println(menu);
    }

    @Test
    public void LIMIT_test(){
        int offset = 0;
        int dp = 10;

        String jpql = "SELECT m FROM menu1 m ORDER BY m.menuCode DESC";

        List<Menu> menuList = em.createQuery(jpql, Menu.class)
                .setFirstResult(offset)
                .setMaxResults(dp)
                .getResultList();
        menuList.forEach(System.out::println);
    }

    @Test
    public void statistics_COUNT_test(){
        String jpql = "SELECT COUNT(m.menuCode) FROM menu1 m WHERE m.categoryCode = ?1";

        Integer categoryCode = 1;

        long cnt = em.createQuery(jpql, Long.class)
                .setParameter(1, categoryCode)
                .getSingleResult();
        System.out.println(cnt);
    }

    @Test
    public void statistics_except_count_test(){
        String jpql = "select SUM(m.menuPrice) from menu1 m where m.categoryCode = ?1";

        Integer categoryCode = 10;

        long totalPrice = em.createQuery(jpql, Long.class)
                .setParameter(1, categoryCode)
                .getSingleResult();

        System.out.println(totalPrice);

    }
}
