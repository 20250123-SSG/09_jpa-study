package com.ino.section01.many_to_one;


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
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

//    @Column(name = "category_code")
//    private Integer categoryCode; // nullable -> wrapper type 사용

    @ManyToOne(
            fetch = FetchType.EAGER
            , cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "category_code")
    private Category category;


    @Column(name = "orderable_status")
    private String orderableStatus; // char
}
