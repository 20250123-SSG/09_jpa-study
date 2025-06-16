package com.younggalee.section03.bidirection;


import jakarta.persistence.*;
import lombok.*;
import org.junit.jupiter.api.Test;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity(name = "menu3")
@Table(name = "tbl_menu")
public class Menu {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private int menuCode;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name = "category_code") //joinColum 있는 쪽이 주인 엔티티 (상위)
    private Category categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;


}
