package com.ino.section01;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "tbl_test")
public class Test1 {

    @Id
    @Column(name = "test_no")
    private int testNo;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_date")
    private Date testDate;
}
