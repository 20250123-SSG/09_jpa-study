package com.younggalee.section03.join;

import jakarta.persistence.*;
import lombok.*;
import org.junit.jupiter.api.Test;

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
    private Integer refCategoryCode;


    //양방향이 왜 필요한가? -> 카테고리 상세 페이지에서 그 카테고리에 속한 메뉴들을 보여줘야하는 경우
    @OneToMany(mappedBy = "category") // menu의 category필드와 매핑됨
    private List<Menu> menuList;

    @Override
    public String toString(){
        return "Category(categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refcategoryCode=" + refCategoryCode + ")";
    }

}

