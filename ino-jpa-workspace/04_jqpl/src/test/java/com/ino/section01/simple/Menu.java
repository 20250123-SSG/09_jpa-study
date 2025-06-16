package com.ino.section01.simple;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "menu1") // 다른 패키지에 동일 엔티티명이 존재할경우 구분을 위해
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @Column(name = "menu_code")
    private Integer menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private Integer menuPrice;

    @Column(name = "category_code")
    private Integer categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;



}
