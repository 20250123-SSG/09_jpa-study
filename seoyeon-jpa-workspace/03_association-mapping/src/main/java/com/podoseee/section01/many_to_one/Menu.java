package com.podoseee.section01.many_to_one;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity(name="menu1")
@Table(name="tbl_menu")
public class Menu { // M

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_code")
    private int menuCode;
    @Column(name="menu_name")
    private String menuName;
    @Column(name="menu_price")
    private int menuPrice;

    //@Column(name="category_code")
    //private Integer categoryCode;

    @ManyToOne(
            fetch=FetchType.EAGER // 즉시로딩 (@ManyToOne 에서 생략시 기본값)
            //fetch=FetchType.LAZY // 지연로딩
            , cascade=CascadeType.PERSIST
    )
    @JoinColumn(name="category_code")
    private Category category;

    @Column(name="orderable_status")
    private String orderableStatus;

}