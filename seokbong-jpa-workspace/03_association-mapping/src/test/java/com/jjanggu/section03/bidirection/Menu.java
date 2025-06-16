package com.jjanggu.section03.bidirection;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity(name="menu3")
@Table(name="tbl_menu")
public class Menu {

    @Id
    @Column(name="menu_code")
    private int menuCode;
    @Column(name="menu_name")
    private String menuName;
    @Column(name="menu_price")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name="category_code")
    private Category category;

    @Column(name="orderable_status")
    private String orderableStatus;

}


