package com.kyungbae.section02.projection;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "menu2")
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
    @Embedded // @Embeddable 클래스를 포함시킬 때 표현
    private MenuInfo menuInfo;

    @Column(name = "category_code")
    private Integer categoryCode;
    @Column(name = "orderable_status")
    private String orderableStatus;

}
