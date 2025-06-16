package com.kyungbae.section04.practice;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity(name = "member")
@Table(name = "tbl_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int memberId;

    @Column(name = "member_name")
    private String memberName;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "locker_id")
    private Locker locker;

}
