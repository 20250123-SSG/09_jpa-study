package com.ino.section04.practice;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "tbl_member")
public class Member {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column
    private String memberName;

    @ManyToOne(
            fetch = FetchType.EAGER
            , cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "teamId")
    private Team team;

    @OneToOne(
            fetch = FetchType.LAZY
            , cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "lockerId")
    private Locker locker;
} 
