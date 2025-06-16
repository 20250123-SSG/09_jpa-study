package com.jjanggu.section03.bidirection;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
// @ToString

@Entity(name="category3")
@Table(name="tbl_category")
public class Category {
    @Id
    @Column(name="category_code")
    private int categoryCode;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(mappedBy="category")
    private List<Menu> menuList;

    public String toString(){
        return "Category(categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode=" + refCategoryCode + ")";
    }
}