package com.younggalee.section04.subquery;
import com.younggalee.section03.join.Category;
import jakarta.persistence.*;

@Entity(name = "menu4")
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