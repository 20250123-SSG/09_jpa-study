package com.younggalee.section03;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/*
    ## 컬럼 매핑시 @Column에 사용 가능한 속성들 ##
    1. name             : 매핑할 테이블의 컬럼명
    2. insertable       : 엔티티 저장시 필드 저장 여부 (default:true)
    3. updatable        : 엔티티 수정시 필드 수정 여부 (default:true)
    4. table            : 엔티티와 매핑될 테이블 이름, 하나의 엔티티를 두개 이상의 테이블에 매핑할 때 사용(@SecondaryTable사용)
    5. nullable         : null 허용 여부 설정, not null 제약조건에 해당함 (default:true)
    6. unique           : 컬럼의 유일성 제약조건에 해당함 (default:false), 두 개 이상의 컬럼을 묶어 부여하고자 할 경우 클래스 레벨에서 uniqueConstraints 속성을 활용
    7. columnDefinition : 직접 컬럼의 DDL을 지정
    8. length           : 문자열 길이, String 타입에서만 사용 (default:255)
 */

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
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "user_no")
    private int userNo;
    @Column(name="user_id", nullable = false)
    private String userId;
    @Column(name = "user_pwd", nullable = false)
    private String userPwd;
    @Column(name = "nickname", nullable = false)
    private String nickName;
    @Column(columnDefinition = "varchar(13) default '010-0000-0000'")
    private String phone;
    @Column(unique = true)
    private String email;
    @Column
    private String address;
    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;
    @Column(name = "user_role")
    @Enumerated(EnumType.ORDINAL) // 이게 default : 값이 아닌 상수선언순서가 기록됨. 따라서 값이 0, 1 이런식임 + 타입: tinyint
//    @Enumerated(EnumType.STRING) // 상수명으로 기록됨. 타입 : enum / ADMIN, USER 들어감
    private UserRole userRole; // 미리 정의된 값만 들어올 수 있게
    @Column(length = 1)
    private String status;
}
