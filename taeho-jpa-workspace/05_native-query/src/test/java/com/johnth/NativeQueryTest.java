package com.johnth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class NativeQueryTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() { entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test"); }
    @BeforeEach
    public void initEntityManager(){ entityManager = entityManagerFactory.createEntityManager(); }
    @AfterEach
    public void destroyEntityManager(){ entityManager.close(); }
    @AfterAll
    public static void destroyEntityManagerFactory() { entityManagerFactory.close(); }

    /*
        ## Native Query ##
        1. SQL 쿼리를 그대로 사용하는 걸 의미
        2. ORM 기능을 이용하면서 SQL 쿼리도 활용할 수 있어 강력하게 DB 접근이 가능
        3. 복잡한 쿼리를 작성해야되거나, 특정 데이터베이스에서만 사용 가능한 기능을 써야될 때 Native Query 활용
        4. Native Query API
           1) 결과 타입 명시
              Query createNativeQuery(String sqlString, Class resultClass)
           2) 결과 타입 명시 x
              Query createNativeQuery(String sqlString)
           3) 결과 매핑 사용
              Query createNativeQuery(String sqlString, String resultSetMapping)
     */

    @Test
    public void 결과_타입_명시_테스트(){
        int menuCode = 15;

        String sql = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status FROM tbl_menu WHERE menu_code = ?";

        Menu foundMenu = (Menu)entityManager.createNativeQuery(sql, Menu.class).setParameter(1, menuCode).getSingleResult();

        System.out.println(foundMenu);

    }

    @Test
    public void 결과_타입_명시_x_테스트(){
        String sql = "SELECT menu_code, menu_name FROM tbl_menu";

        List<Object[]> menuList = entityManager.createNativeQuery(sql).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void 실습1(){
        // 모든 메뉴 조회시 카테고리명도 같이 조회하시오.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT menu_code, menu_name, menu_price, m.category_code, orderable_status, category_name");
        sql.append("  FROM tbl_menu m");
        sql.append("  JOIN tbl_category c ON m.category_code = c.category_code");

        List<Object[]> menuList = entityManager.createNativeQuery(sql.toString()).getResultList();

        menuList.forEach(row -> System.out.println(Arrays.toString(row)));

    }

    @Test
    public void 실습2(){
        // 모든 서브 카테고리 정보를 조회하시오.
        /*
        String sql = """
            SELECT category_code, category_name, ref_category_code
              FROM tbl_category
             WHERE ref_category_code IS NOT NULL""";

        List<Category> categoryList = entityManager.createNativeQuery(sql, Category.class).getResultList();
        categoryList.forEach(System.out::println);
         */

        // 모든 서브 카테고리 조회시 카테고리에 속한 메뉴의 개수를 함께 조회하시오.
        String sql = """
            SELECT c.category_code, category_name, ref_category_code,
                   (SELECT COUNT(*)
                      FROM tbl_menu m
                     WHERE m.category_code = c.category_code) menu_count
              FROM tbl_category c
             WHERE ref_category_code IS NOT NULL""";

        List<Object[]> categoryList = entityManager.createNativeQuery(sql).getResultList();
        categoryList.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    @Test
    public void 결과_매핑_테스트1(){
        String sql = """
            SELECT c.category_code, category_name, ref_category_code,
                   (SELECT COUNT(*)
                      FROM tbl_menu m
                     WHERE m.category_code = c.category_code) menu_count
              FROM tbl_category c
             WHERE ref_category_code IS NOT NULL""";

        // category_code, category_name, ref_category_code => Category 엔티티로 매핑
        // menu_count => 별도로 취급
        // Object[Category, menu_count]

        List<Object[]> categoryList = entityManager.createNativeQuery(sql, "categoryAndMenuCountMapping1").getResultList();
        categoryList.forEach(row -> System.out.println(Arrays.toString(row)));

    }

    @Test
    public void 결과_매핑_테스트2(){
        String sql = """
            SELECT c.category_code, category_name, ref_category_code,
                   (SELECT COUNT(*)
                      FROM tbl_menu m
                     WHERE m.category_code = c.category_code) menu_count
              FROM tbl_category c
             WHERE ref_category_code IS NOT NULL""";

        List<CategoryDto> categoryList = entityManager.createNativeQuery(sql, "categoryAndMenuCountMapping2").getResultList();

        categoryList.forEach(System.out::println);
    }


}






