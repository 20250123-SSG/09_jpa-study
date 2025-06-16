package com.younggalee.section02.one_to_many;

import jakarta.persistence.*;
import lombok.*;
import org.junit.jupiter.api.Test;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity(name = "menu2")
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
    @Column(name = "category_code")
    private Integer categoryCode;
    @Column(name = "orderable_status")
    private String orderableStatus;


}
