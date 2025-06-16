package com.kyungbae.section01.many_to_one;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity(name = "menu1")
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

//    @Column(name = "category_code")
//    private int categoryCode;
    @ManyToOne(
            fetch = FetchType.EAGER // 즉시로딩 (default)
//            fetch = FetchType.LAZY // 지연로딩
            , cascade = CascadeType.PERSIST // 영속성 전이 설정
    )
    @JoinColumn(name = "category_code")
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;

}
