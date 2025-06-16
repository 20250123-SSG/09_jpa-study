package com.younggalee.section02.projection;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Embeddable // 엔티티의 일부 데이터를 가지는 클래스
public class MenuInfoEmbeded {
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_price")
    private Integer menuPrice;
}
