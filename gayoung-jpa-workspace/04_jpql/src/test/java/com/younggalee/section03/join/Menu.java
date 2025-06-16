package com.younggalee.section03.join;
import jakarta.persistence.*;

@Entity(name = "menu3")
@Table(name = "tbl_menu")
public class Menu {
    @Id
    @Column(name = "menu_code")
    private Integer menuCode;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private Integer menuPrice;

    @ManyToOne //하나의 메뉴는 하나의 카테고리에 속한다
    @JoinColumn(name = "category_code")  //연관관계 매핑된 모습 / 해당키를 외래키로 사용
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;

}