package com.ino.section01;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity(name = "test")
public class Test2 {
    @Id
    @Column(name = "test_no")
    private int testNo;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_date")
    private Date testDate;
}

/*
Hibernate:
    drop table if exists test
6월 04, 2025 3:49:37 오후 org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl getIsolatedConnection
INFO: HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@74c121d4] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
Hibernate:
    create table test (
        test_no integer not null,
        test_date datetime(6),
        test_name varchar(255),
        primary key (test_no)
    ) engine=InnoDB
 */