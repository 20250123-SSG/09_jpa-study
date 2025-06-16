package com.ino.section03.bidirection;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity(name="category3")
@Table(name="tbl_category")
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode; // 값이 없을 수도 있으므로 Wrapper 타입인 Integer 사용

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList;

    public String toString(){
        return "categoryCode : " + categoryCode + ", categoryName : " + categoryName + ", ref_cate_code : " + refCategoryCode;
    }
}