package com.jjanggu.section03.join;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString

@Entity(name = "category3")
@Table(name = "tbl_category")
public class Category {
    @Id
    @Column(name = "category_code")
    private Integer categoryCode;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "ref_category_code")
    private Integer refCategory;

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList;

    public String toString(){
        return "Category(categoryCode=" + categoryCode + ", categoryName=" + categoryName + "refCategory=" + refCategory + ")";

    }
}
