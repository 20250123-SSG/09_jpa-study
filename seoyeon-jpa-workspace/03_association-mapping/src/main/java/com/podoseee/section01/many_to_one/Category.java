package com.podoseee.section01.many_to_one;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity(name="category1")
@Table(name="tbl_category")
public class Category { // 1

    @Id
    @Column(name="category_code")
    private int categoryCode;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="ref_category_code")
    private Integer refCategoryCode;

}