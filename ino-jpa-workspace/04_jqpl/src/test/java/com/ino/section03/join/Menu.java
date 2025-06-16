package com.ino.section03.join;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "menu3") // 다른 패키지에 동일 엔티티명이 존재할경우 구분을 위해
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @Column(name = "menu_code")
    private Integer menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private Integer menuPrice;

    @Column(name = "orderable_status")
    private String orderableStatus;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

}
