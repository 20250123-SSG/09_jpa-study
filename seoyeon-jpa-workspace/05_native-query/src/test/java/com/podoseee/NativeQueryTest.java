package com.podoseee;

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
        sql.append("SELECT m.menu_code, m.menu_name, m.category_code, m.orderable_status, c.category_name ");
        sql.append("  FROM tbl_menu m");
        sql.append("  JOIN tbl_category c ON m.category_code = c.category_code");

        List<Object[]> menuList = entityManager.createNativeQuery(sql.toString()).getResultList();

        menuList.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    @Test
    public void 실습2(){
        /*
        // 모든 서브 카테고리 정보를 조회하시오.
        String sql = "SELECT category_code, category_name, ref_category_code " +
                "FROM tbl_category " +
                "WHERE ref_category_code IS NOT NULL";

        List<Object[]> resultList = entityManager.createNativeQuery(sql).getResultList();

        resultList.forEach(row -> {
            System.out.println("category_code: " + row[0]
                    + ", category_name: " + row[1]
                    + ", ref_category_code: " + row[2]);
        });
         */

        // 모든 서브 카테고리 조회시 카테고리에 속한 메뉴의 개수를 함께 조회하시오.
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT c.category_code, c.category_name, c.ref_category_code, COUNT(m.menu_code) AS menu_count ");
            sql.append("  FROM tbl_category c ");
            sql.append("  LEFT JOIN tbl_menu m ON c.category_code = m.category_code ");
            sql.append(" WHERE c.ref_category_code IS NOT NULL ");
            sql.append(" GROUP BY c.category_code, c.category_name, c.ref_category_code");

            List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();

            resultList.forEach(row -> {
                System.out.println("category_code: " + row[0]
                        + ", category_name: " + row[1]
                        + ", ref_category_code: " + row[2]
                        + ", menu_count: " + row[3]);
            });
        }

    @Test
    public void 결과_매핑_테스트1(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.category_code, c.category_name, c.ref_category_code, COUNT(m.menu_code) AS menu_count ");
        sql.append("  FROM tbl_category c ");
        sql.append("  LEFT JOIN tbl_menu m ON c.category_code = m.category_code ");
        sql.append(" WHERE c.ref_category_code IS NOT NULL ");
        sql.append(" GROUP BY c.category_code, c.category_name, c.ref_category_code");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString(), "categoryAndMenuCountMapping1").getResultList();

        resultList.forEach(row -> {
            Category category = (Category) row[0];
            Integer menuCount = (Integer) row[1];

            System.out.println("category: " + category);
            System.out.println("menu_count: " + menuCount);
            System.out.println("------------------------------");
        });
    }

    @Test
    public void 결과_매핑_테스트2(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.category_code, c.category_name, COUNT(m.menu_code) AS menu_count ");
        sql.append("  FROM tbl_category c ");
        sql.append("  LEFT JOIN tbl_menu m ON c.category_code = m.category_code ");
        sql.append(" WHERE c.ref_category_code IS NOT NULL ");
        sql.append(" GROUP BY c.category_code, c.category_name");

        List<CategoryDto> categoryDtoList = entityManager
                .createNativeQuery(sql.toString(), "categoryAndMenuCountMapping2")
                .getResultList();

        categoryDtoList.forEach(dto -> System.out.println(dto));
    }
}
