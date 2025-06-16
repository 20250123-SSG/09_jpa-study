package com.ino.section03;

import com.ino.entity.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

public class EntityLifeCycleTest {

    private static EntityManagerFactory entityManagerFactory; // Em 생성을 위한 인스턴스, 싱글톤 관리 권장
    private EntityManager entityManager; // 엔티티 관리 인스턴스, 엔티티 CRUD와 관련된 task 진행

    @BeforeAll //  테스트 클래스의 인스턴스가 생성되기 전에 해당 메서드가 실행되기 때문에 static 지정 필
    public static void initEntityManagerFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test"); //   <persistence-unit name="jpa_test">로 정의한 이름 설정
    }

    @BeforeEach
    public void initEntityManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager(){
        entityManager.close();;
    }

    @AfterAll
    public static void destroyEntityManagerFactory(){
        entityManagerFactory.close();
    }

    @Test
    public void transient_status_test(){
        Menu menu = Menu.builder()
                .menuCode(3)
                .menuName("생갈치쉐이크")
                .menuPrice(6000)
                .categoryCode(10)
                .orderableStatus("Y")
                .build();

        Menu foundedMenu = entityManager.find(Menu.class, 3);
        Assertions.assertThat(foundedMenu).isNotEqualTo(menu);
    }

    @Test
    public void managed_status_find_test(){

        int menuCode = 3;

        Menu founded1 = entityManager.find(Menu.class, menuCode);

        Menu founded2 = entityManager.find(Menu.class, menuCode);

        Assertions.assertThat(founded1).isEqualTo(founded2);
    }

    @Test
    public void managed_persist_test(){
        Menu menu = Menu.builder()
                .menuCode(30)
                .menuName("watermelonsoup")
                .menuPrice(10000)
                .categoryCode(1)
                .orderableStatus("Y")
                .build();

        EntityTransaction et = entityManager.getTransaction();

        et.begin();
        try {
            entityManager.persist(menu);
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }

        Assertions.assertThat(entityManager.find(Menu.class, menu.getMenuCode())).isEqualTo(menu);
    }

    @Test
    public void detached_method_test(){
        Menu founded1 = entityManager.find(Menu.class, 20);
        Menu founded2 = entityManager.find(Menu.class, 21);

        entityManager.detach(founded1);

        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        founded1.setMenuPrice(100);
        founded2.setMenuPrice(200);
        Assertions.assertThat(entityManager.find(Menu.class, 21).getMenuPrice()).isEqualTo(200);
        Assertions.assertThat(entityManager.find(Menu.class, 20).getMenuPrice()).isNotEqualTo(100);
    }

    @Test
    public void 준영속_clear_test(){
        Menu founded1 = entityManager.find(Menu.class, 20);
        Menu founded2 = entityManager.find(Menu.class, 21);

        entityManager.clear();

        founded1.setMenuPrice(200);
        founded2.setMenuPrice(200);


        Assertions.assertThat(entityManager.find(Menu.class, 21).getMenuPrice()).isNotEqualTo(200);
        Assertions.assertThat(entityManager.find(Menu.class, 20).getMenuPrice()).isNotEqualTo(200);
    }

    @Test
    public void detached_to_manager_merge_test(){
        Menu founded = entityManager.find(Menu.class, 1); // managed status object
        entityManager.detach(founded); // managed -> detached
        founded.setMenuName("helloworld");
        Menu Merged = entityManager.merge(founded); // detached -> managed (1차 캐시 조회 -> 없음(detach됐기에) -> processing query)

        Assertions.assertThat(entityManager.find(Menu.class, 1).getMenuName()).isEqualTo("helloworld");

    }

    @Test
    public void detached_merge_save_test(){
        int menuCode = 1;
        Menu founded = entityManager.find(Menu.class, menuCode);
        entityManager.detach(founded);

        founded.setMenuName("latte");
        founded.setMenuCode(100);

        Menu mergedMenu = entityManager.merge(founded);
        // 1) foundedMenu id(100)를 통해 1차 캐시로부터 조회 (존재x)
        // 2) DB로부터의 조회 ( 존재x)
        // 3) 새로운 Entity 생성 및 병합(준영속 상태의 객체와) 및 반환
        // 4) 해당 Entity는 Persistence Context에 저장

        System.out.println(mergedMenu);
    }

    @Test
    public void remove_test(){
        int menuCode = 1;

        Menu founded = entityManager.find(Menu.class, menuCode); // managed status

        entityManager.remove(founded); // removed status, still on control

        Menu removedMenu = entityManager.find(Menu.class, menuCode); // 삭제 상태임을 인지 및 NULL 반환


    }
}
