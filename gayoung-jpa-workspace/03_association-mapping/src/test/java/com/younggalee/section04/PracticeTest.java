package com.younggalee.section04;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PracticeTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory(){
        entityManagerFactory = // persistance.xml을 읽고 설정을 로딩하여 managerFactory를 생성하는 코드

        //Persistence는 헬퍼 클래스이기 때문에 객체 생성에 있어 new를 사용하지 않는다.
        // 1. 헬퍼 클래스 : 작업을 도와주는 유틸리티성 기능만 있는 클래스로 대부분 static메소드로 이루어져 객체 생성없이 메서드를 바로 쓸 수 있음
        //     > Persistence는
    }

    @Test
    public void DB_잘생성되는지_테스트() {

    }
    /*
        예제.
        한 팀에는 여러 회원들이 존재하며 각 회원들은 각각 하나의 라커를 가지고 있다.
        팀 - 회원 - 라커
        1 : M
            1  :  1
     */
}
