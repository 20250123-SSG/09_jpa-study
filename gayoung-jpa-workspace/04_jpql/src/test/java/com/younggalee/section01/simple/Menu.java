package com.younggalee.section01.simple;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "menu1") // 반드시 pk가 있어야함 (+엔티티명) @Id
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
