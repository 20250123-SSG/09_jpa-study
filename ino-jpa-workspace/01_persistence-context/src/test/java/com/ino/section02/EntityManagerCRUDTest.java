package com.ino.section02;

import com.ino.entity.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

public class EntityManagerCRUDTest {

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
    }

    @AfterAll
    public static void destroyEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @Test
    public void req1() {
        int menuCode = 2;

        Menu foundedMenu = entityManager.find(Menu.class, menuCode);

        Assertions.assertThat(foundedMenu).isNotNull();
        Assertions.assertThat(foundedMenu.getMenuName()).isEqualTo("우럭스무디");

        System.out.println(foundedMenu);
    }

    @Test
    public void regist_new_menu_test(){
        // given

        // construct entity object
        Menu menu = Menu.builder()
                .menuName("americano")
                .menuPrice(2000)
                .categoryCode(4)
                .orderableStatus("Y")
                .build();

        // transaction 처리 위한 객체 생성
        EntityTransaction et = entityManager.getTransaction();
        // 트랜잭션 시작
        et.begin();

        // 엔티티 객체 영속성 컨텍스트에 저장(쿼리 진행 위해)
        entityManager.persist(menu);


        try {
            // 영속성 컨텍스트에 저장된 data와 실제 db를 비교하여 새로운 내용을 저장하는 곳(엔티티매니저가 관리하는 모든 엔티티를 실제 db에 반영하는)
            et.commit();
        } catch (Exception e) {
            et.rollback(); // rollback -> em이 관리하는 모든 엔티티를 이전 상태로 되돌림
            e.printStackTrace();
        }

        Assertions.assertThat(entityManager.contains(menu)).isTrue();


    }

    @Test
    public void edit_menuName_test(){

        int menuCode = 2;
        String menuNameToChange = "갈치스무디";


        // 수정 엔티티 조회 -> 영속성 컨텍스트 저장
        Menu menu = entityManager.find(Menu.class, menuCode);

        EntityTransaction et = entityManager.getTransaction();
        et.begin();

        try {
            menu.setMenuName(menuNameToChange);
            et.commit(); // 변경 감지 및 변경여부 o => update query 날림
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }

        Assertions.assertThat(entityManager.find(Menu.class, menuCode).getMenuName()).isEqualTo(menuNameToChange);
    }


    @Test
    public void delete_menu_test(){

        int menuCode = 14;

        Menu menu = entityManager.find(Menu.class, menuCode);

        EntityTransaction et = entityManager.getTransaction();
        et.begin();

        try {
            entityManager.remove(menu);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }

        Assertions.assertThat(entityManager.find(Menu.class, menuCode)).isNull();
    }
}
