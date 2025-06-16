package com.ino;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class NativeQueryTest {

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
    public void result_type_test(){
        int menuCode = 15;

        // entity 중심 x, db중심이므로 그냥 db명 사용하면됨
        String sql = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status FROM tbl_menu WHERE menu_code = ?";
        Menu menu = (Menu) em.createNativeQuery(sql, Menu.class).setParameter(1, menuCode).getSingleResult();
        // menuEntity에 매핑하도록 구현은 했으나,menuType 변수에 담고자 할 경우 다운캐스팅 필요

        System.out.println(menu);
    }

    @Test
    public void result_type_not_test(){
        String sql = "SELECT menu_code, menu_name FROM tbl_menu";

        List<Object[]> menuList = em.createNativeQuery(sql).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void exam1(){
        String sql = "SELECT m.menu_code, m.menu_name, m.menu_price, m.category_code, m.orderable_status, c.category_name FROM tbl_menu m JOIN tbl_category c USING(category_code)";
        List<Object[]> menuList = em.createNativeQuery(sql).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void exam2(){
        StringBuilder sb = new StringBuilder();
        // sub Cate + 메뉴 개수 조회
        sb.append("SELECT c.category_code, category_name, ref_category_code, ");
        sb.append("(SELECT COUNT(*)");
        sb.append("FROM tbl_menu m");
        sb.append(" WHERE m.category_code = c.category_code) menu_count");
        sb.append(" FROM tbl_category c");
        sb.append("     WHERE ref_category_code IS NOT NULL");
        List<Object[]> categoryList = em.createNativeQuery(sb.toString()).getResultList();

        categoryList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void result_mapping_test1(){
        StringBuilder sb = new StringBuilder();
        // sub Cate + 메뉴 개수 조회
        sb.append("SELECT c.category_code, category_name, ref_category_code, ");
        sb.append("(SELECT COUNT(*)");
        sb.append("FROM tbl_menu m");
        sb.append(" WHERE m.category_code = c.category_code) menu_count");
        sb.append(" FROM tbl_category c");
        sb.append("     WHERE ref_category_code IS NOT NULL");
        List<Object[]> categoryList = em.createNativeQuery(sb.toString(), "categoryAndMenuCountMapping1").getResultList();

        categoryList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void result_mapping_test2(){
        StringBuilder sb = new StringBuilder();
        // sub Cate + 메뉴 개수 조회
        sb.append("SELECT c.category_code, category_name, ref_category_code, ");
        sb.append("(SELECT COUNT(*)");
        sb.append("FROM tbl_menu m");
        sb.append(" WHERE m.category_code = c.category_code) menu_count");
        sb.append(" FROM tbl_category c");
        sb.append("     WHERE ref_category_code IS NOT NULL");
        List<CategoryDto> categoryList = em.createNativeQuery(sb.toString(), "categoryAndMenuCountMapping2").getResultList();

        categoryList.forEach(System.out::println);
    }


}
