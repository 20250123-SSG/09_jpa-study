package com.podoseee.section04.practice;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity(name="member")
@Table(name="tbl_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private int memberId;
    @Column(name="member_name")
    private String memberName;

    @ManyToOne(
            fetch = FetchType.EAGER // member 조회시 team에 대한 정보 조회도 즉시 수행하겠다.
            , cascade = CascadeType.PERSIST
    )
    @JoinColumn(name="team_id")
    private Team team;

    @OneToOne(
            fetch = FetchType.LAZY  // member 조회시 locker에 대한 정보 조회를 즉시 수행하지 않겠다. (사용할 때 조회하겠다.)
            , cascade = CascadeType.PERSIST
    )
    @JoinColumn(name="locker_id")
    private Locker locker;

}