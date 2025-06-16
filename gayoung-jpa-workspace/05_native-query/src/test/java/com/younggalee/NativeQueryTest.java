package com.younggalee;

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
    public static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPA_Test");
    }

    @BeforeEach
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager() {
        entityManager.close();
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    /*
        ## Native Query ##
        1. SQL 쿼리를 그대로 사용하는걸 의미
        2. ORM 기능을 이용하면서 SQL쿼리도 활용할 수 있어 강력하게 DB 접근이 가능
        3. 복잡한 쿼리를 작성해야되거나, 특정 데이터베이스에서만 사용 가능한 기능을 써야될때 Native Query 활용
        4. Native Query API
            1) 결과 타입 명시     (String sqlString, Class resultClass)
            2) 결과 타입 명시 X   (String sqlString)
            3) 결과 매핑 사용     (String sqlString, String resultSetMapping)
     */
    @Test
    public void 결과_타입_명시_테스트() {
        int menuCode = 12;

        //sql문법대로 쓰기 (엔티티중심 아니고, DBMS)
        String sql = "SELECT * FROM tbl_menu WHERE menu_code = ?"; //위치기반 파라미터 바운딩

        Menu foundMenu = (Menu) entityManager.createNativeQuery(sql, Menu.class).setParameter(1, menuCode).getSingleResult(); // 제네릭 결과타입 명시
                                                // Menu로 반환
        System.out.println(foundMenu.toString());
    }

    @Test
    public void 결과_타입_명시_X_테스트() {
        int menuCode = 12;

        //sql문법대로 쓰기 (엔티티중심 아니고, DBMS)
        String sql = "SELECT menu_code, menu_name FROM tbl_menu WHERE menu_code = ?"; //전체 데이터 가져오는게 아니라 메뉴 코드와 메뉴 이름만 가져오기


        //JPA에서 createNativeQuery(sql)로 결과 타입을 명시하지 않고, SQL에서 여러 컬럼을 선택하면, JPA는 각 컬럼 값을 Object[]에 담아 반환합니다.
        List<Object[]> foundMenu = entityManager.createNativeQuery(sql).setParameter(1, menuCode).getResultList(); // 제네릭 결과타입 명시
                                               // Object[] 에 여러 컬럼 담아서 반환
        foundMenu.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });

    }

    @Test
    public void 실습1() {
        // 모든 메뉴 조회시 카테고리명(카테고리테이블)도 같이 조회하기
        String sql = "SELECT menu_code, menu_name, category_name FROM tbl_menu m JOIN tbl_category c ON m.category_code = c.category_code";  // jdbc처럼 동작 엔티티없어도 동작함.
        List<Object[]> foundMenu = entityManager.createNativeQuery(sql).getResultList(); // 제네릭 결과타입 명시
        // Object[] 에 여러 컬럼 담아서 반환
        foundMenu.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    public void 실습2() {
        // 모든 메뉴 조회시 카테고리명(카테고리테이블)도 같이 조회하기
        String sql = "SELECT menu_code, menu_name, category_name FROM tbl_menu m JOIN tbl_category c ON m.category_code = c.category_code";  // jdbc처럼 동작 엔티티없어도 동작함.
        List<Object[]> foundMenu = entityManager.createNativeQuery(sql, "categoryAndMenuCountMapping2").getResultList(); // 제네릭 결과타입 명시
        // Object[] 에 여러 컬럼 담아서 반환
        foundMenu.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }


}




















