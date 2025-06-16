package com.kyungbae.section04.practice;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity(name = "locker")
@Table(name = "tbl_locker")
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id")
    private int lockerId;

    @Column(name = "locker_name")
    private String lockerName;

}
