package com.ino.section02.projection;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "menu2") // 다른 패키지에 동일 엔티티명이 존재할경우 구분을 위해
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @Column(name = "menu_code")
    private Integer menuCode;


    /*
    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private Integer menuPrice;
*/

    @Embedded
    private MenuInfo menuInfo;

    @Column(name = "category_code")
    private Integer categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

}
