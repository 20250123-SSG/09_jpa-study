package com.younggalee.section01.many_to_one;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.namespace.QName;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "category1")
@Table(name = "tbl_category")
public class Category {  // 1

    @Id
    @Column(name = "category_code")
    private int categoryCode;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "ref_category_code")
    private Integer refCategoryCode; // null인 경우도 있어서

}
