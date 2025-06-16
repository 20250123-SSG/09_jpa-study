package com.younggalee.section01.many_to_one;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "menu1")
@Table(name = "tbl_menu")
public class Menu {  // M

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private String menuCode;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private int menuPrice;

//    @Column(name = "category_code")
//    private Integer categoryCode;  // int대신 integer 사용하는 이유 : null 값을 허용하기 위해서 int면 0, 참조형은 null 가능. 따라서 자바 객체의 필드에서도 null값을 다루려면 integer (의미 그대로 반영)

    @ManyToOne( //어떤 컬럼을 가지고 조인을 할건지 조인컬럼설정해줘야함
            //fetch = FetchType.EAGER // 즉시로딩 (default) : 처음부터 menu, 카테고리 정보 모두 조회해옴
            fetch = FetchType.LAZY // 지연로딩 - 엔티티에 대한 정보가 사용될 때 조회됨. // 따라서 성능면에서 우수함
            // menu에 대해 우선 조회해하고(카테고리코드까지), 카테고리에 대한 정보를 사용할때, (카테고리코드를 통해)카테고리 정보를 조회해옴

            , cascade = CascadeType.PERSIST // 영속성 전이 설정
            // 하위객체가 persist 될때 상위 테이블에 먼저 persist 해주는 설정
    )
    @JoinColumn(name = "category_code") // 조인속성지정
    private Category category; // 카테고리라는 엔티티자체를 가지고 있게 함.

    @Column(name = "orderable_status")
    private String orderableStatus;
}
