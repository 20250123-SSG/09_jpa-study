package com.podoseee.section03.join;

import com.podoseee.section02.projection.CategoryResponseDto;
import com.podoseee.section02.projection.MenuInfo;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity(name="menu3")
@Table(name="tbl_menu")
public class Menu {

    @Id
    @Column(name="menu_code")
    private Integer menuCode;
    @Column(name="menu_name")
    private String menuName;
    @Column(name="menu_price")
    private Integer menuPrice;

    @ManyToOne
    @JoinColumn(name="category_code")
    private Category category;

    @Column(name="orderable_status")
    private String orderableStatus;

}
