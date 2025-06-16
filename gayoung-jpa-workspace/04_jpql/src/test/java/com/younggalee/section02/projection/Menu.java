package com.younggalee.section02.projection;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "menu2") // 반드시 pk가 있어야함 (+엔티티명) @Id
@Table(name = "tbl_menu")
public class Menu {
    @Id
    @Column(name = "menu_code")
    private Integer menuCode;


//    @Column(name = "menu_name")
//    private String menuName;
//    @Column(name = "menu_price")
//    private Integer menuPrice;

    @Embedded // 임베디드 타입 클래스를 포함시킬때 표현
    private MenuInfoEmbeded menuInfo;


    @Column(name = "category_code")
    private Integer categoryCode;
    @Column(name = "orderable_status")
    private String orderableStatus;

}
