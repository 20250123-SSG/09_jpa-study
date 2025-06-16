package com.jjanggu.section03;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity
@Table(name = "tbl_user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "user_pwd", nullable = false)
    private String userPwd;;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "phone", columnDefinition = "varchar(13) default '010-0000-0000'")
    private String phone;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;

    @Column(name = "user_role")
    //@Enumerated(EnumType.ORDINAL) // 생략시 기본값 => 상수 선언 순서대로 (0부터) 정숙밧 기록 =>tinyint 타입
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "status", length = 1)
    private String status;
}

/*
    ## @Enumerated ##
    1. 종류
        1) EnumType.ORDINAL : 상수 정의 순서로 매핑함 (0부터 시작), 생략시 기본값
        2) EnumType.STRING  : 상수명을 문자열로 매핑함
    2. 종류별 장단점
        1) EnumType.ORDINAL : 데이터 베시스에 저장되는 데이터의 크기가 작음. 단 이미 저장된 enum 순서 변경 불가
        2) EnumType.String  : 데이터베이스에 저장되는 데이터의 크키가 큼, 단. 저장된 enum의 순서가 바뀌거나 새로운 enum이 추가되기 어려움
 */
