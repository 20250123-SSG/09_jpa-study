package com.podoseee.section02.one_to_many;

import com.kangbroo.section01.many_to_one.Category;
import jakarta.persistence.*;
import lombok.*;
import org.junit.jupiter.api.Test;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity(name="menu2")
@Table(name="tbl_menu")
public class Menu { // M
    @Id
    @Column(name="menu_code")
    private int menuCode;
    @Column(name="menu_name")
    private String menuName;
    @Column(name="menu_price")
    private int menuPrice;
    @Column(name="category_code")
    private Integer categoryCode;
    @Column(name="orderable_status")
    private String orderableStatus;

}