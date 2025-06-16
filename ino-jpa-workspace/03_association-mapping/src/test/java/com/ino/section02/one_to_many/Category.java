package com.ino.section02.one_to_many;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity(name="category2")
@Table(name="tbl_category")
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode; // 값이 없을 수도 있으므로 Wrapper 타입인 Integer 사용

    @OneToMany(
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "category_code")
    private List<Menu> menuList;
}