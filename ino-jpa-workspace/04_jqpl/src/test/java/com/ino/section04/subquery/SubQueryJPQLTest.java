package com.ino.section04.subquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

public class SubQueryJPQLTest {
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
    public void subQuery_test(){
        // categoryname= 'hansik'
        String jpql = """
                        SELECT m
                        FROM menu4 m
                         WHERE m.categoryCode = (SELECT c.categoryCode 
                                                   FROM category4 c 
                                                    WHERE c.categoryName = '한식')""";
        List<Menu> list = em.createQuery(jpql, Menu.class).getResultList();
        list.forEach(System.out::println);
    }

    @Test
    public void dynamic_test(){
        String searchKeyworkd = "마늘";

        int searchCategoryCode = 4;

        StringBuilder jpql = new StringBuilder("SELECT m FROM menu4 m");
        if(searchKeyworkd != null && !"".equals(searchKeyworkd) && searchCategoryCode != 0) {
            jpql.append(" WHERE m.menuName LIKE '%' || :keyword || '%'"); // concat으로 자동으로 연이어줌
            jpql.append(" and m.categoryCode = :category");
        } else if (searchKeyworkd != null && !"".equals(searchKeyworkd)) {
            jpql.append(" WHERE m.menuName LIKE '%' || :keyword || '%'");
        } else if (searchCategoryCode != 0) {
            jpql.append(" WHERE m.categoryCode = :category");
        }

        TypedQuery<Menu> query = em.createQuery(jpql.toString(), Menu.class);
        if(searchKeyworkd != null && !"".equals(searchKeyworkd)) {
            query.setParameter("keyword", searchKeyworkd);
        }

        if (searchCategoryCode != 0) {
            query.setParameter("category", searchCategoryCode);
        }

        List<Menu> list = query.getResultList();
    }
}
