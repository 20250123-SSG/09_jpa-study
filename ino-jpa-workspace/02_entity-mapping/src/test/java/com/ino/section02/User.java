package com.ino.section02;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "tbl_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"phone","enroll_date"})}) // phone, enroll_date 묶음, 필드명이 아닌 테이블 칼럼명을 설정해야함
public class User {
    @Id
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pwd", nullable = false)
    private String userPwd;

    @Column(name = "phone", columnDefinition = "varchar(13) default '010-0000-0000'")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "status")
    private String status;
}
