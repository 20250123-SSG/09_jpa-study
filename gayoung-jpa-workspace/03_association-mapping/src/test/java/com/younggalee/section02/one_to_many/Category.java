package com.younggalee.section02.one_to_many;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity(name = "category2")
@Table(name = "tbl_category")
public class Category {  // 1

    @Id
    @Column(name = "category_code")
    private int categoryCode;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name="category_code") // 조인시 해당 컬럼을 참조한다
    private List<Menu> menuList;
}
