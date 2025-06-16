package com.kyungbae.section03;

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
@Table(name="tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pwd", nullable = false)
    private String userPwd;

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
    @Enumerated(EnumType.STRING) // 상수로 기록
//    @Enumerated(EnumType.ORDINAL) // default (상수 선언 순서대로(0부터) 정수값 기록 => tinyint 타입)
    private UserRole userRole;

    @Column(name = "status", length = 1)
    private String status;
}

/*
    ## @Enumerated
    1. Enum 타입 매핑을 위해 사용
    2. 종류
        1) EnumType.ORDINAL : 상수 정의 순서로 매핑함 (0부터 시작), 생략시 기본값
        2) EnumType.STRING  : 상수명을 문자열로 매핑함
    3. 종류별 장단점
        1) EnumType.ORDINAL : 데이터베이스에 저장되는 데이터의 크기가 작음, 이미 저장된 enum의 순서를 변경할 수 없음
        2) EnumType.STRING  :
 */