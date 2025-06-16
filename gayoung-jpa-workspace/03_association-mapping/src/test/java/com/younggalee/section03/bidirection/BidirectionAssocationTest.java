package com.younggalee.section03.bidirection;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

// 양방향은 되도록 쓰지 않는 것이 좋음
public class BidirectionAssocationTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() { entityManagerFactory = Persistence.createEntityManagerFactory("JPA_Test"); }

    @BeforeEach
    public void initEntityManager() { entityManager = entityManagerFactory.createEntityManager(); }

    @AfterEach
    public void destroyEntityManager() { entityManager.close(); }

    @AfterAll
    public static void closeEntityManagerFactory() { entityManagerFactory.close(); }

    @Test
    public void 양방향_연관관계_매핑_조회_테스트(){
        // 메뉴 엔티티 조회
        Menu foundedMenu = entityManager.find(Menu.class, 10); // menu라는 클래스의 정보를 담고있는 class객체(메타정보활용목적:필드,메서드,어노테이션)
        //JPA는 Menu.class를 통해 Menu 엔티티가 어떤 테이블과 매핑되어 있고, 어떤 필드를 가져야 하며, 어떤 방식으로 SQL을 작성해야 하는지를 내부에서 판단합니다.
        // 매니투원은 find만 했을때 사용하지 않더라도 즉시조회됨.
    }
}
