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
@Table(name = "tbl_locker")
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int lockerId;

    @Column
    private String lockerName;
}
