package com.podoseee.section02.one_to_many;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity(name="category2")
@Table(name="tbl_category")
public class Category { // 1

    @Id
    @Column(name="category_code")
    private int categoryCode;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name="category_code")
    private List<Menu> menuList;

}